import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EventEmitter, Output } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-creation',
  templateUrl: './order-creation.html',
  imports: [CommonModule, ReactiveFormsModule]
})
export class OrderCreation implements OnInit {
  @Input() procurementOfficerIdFromParent!: number; // fallback if needed
  // for admin dashboard purpose
  @Output() OrderCreationComplete = new EventEmitter<void>();

  orderForm!: FormGroup;
  rawMaterials: any[] = [];
  selectedMaterial: any = null;
  selectedSuppliers: any[] = [];

  isQuantityAvailable: boolean = false;
  quantityMessage: string = '';
  orderId?: number;
  raw_material?:string;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.orderForm = this.fb.group({
      orderDate: [this.getTodayDate(), Validators.required],
      procurementOfficerId: ['', Validators.required],
      rawMaterialName: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(1)]],
      supplierName: ['', Validators.required],
      unitPrice: [{ value: '', disabled: true }],
      totalAmount: [{ value: '', disabled: true }]
    });

    // Fetch officer id from AuthService or parent
    const officerId = this.procurementOfficerIdFromParent;
    // console.log(officerId);
    this.orderForm.patchValue({ procurementOfficerId: officerId });

    // Load raw materials
    this.http.get<any[]>('http://localhost:8081/raw_material/all')
      .subscribe(data => {
        this.rawMaterials = data.filter(item => item.status === 'Available' || item.status === 'Unavailable');
      });
  }

  // yyyy-mm-dd
  getTodayDate(): string {
    return new Date().toISOString().split('T')[0];
  }




  onRawMaterialSelect(event: any): void {
    const selectedName = event.target.value;
    this.selectedMaterial = this.rawMaterials.find(m => m.name === selectedName);

    if (this.selectedMaterial) {
      this.selectedSuppliers = this.selectedMaterial.suppliers || [];
      this.orderForm.patchValue({ supplierName: '', unitPrice: '', totalAmount: '' });
    }
  }

  checkQuantity(): void {
    const qty = this.orderForm.get('quantity')?.value;
    if (!this.selectedMaterial) return;

    if (qty <= this.selectedMaterial.stockQuantity) {
      this.isQuantityAvailable = true;
      this.quantityMessage = 'Requested quantity is available';
    } else {
      this.isQuantityAvailable = false;
      this.quantityMessage = 'Requested quantity is unavailable';
    }
    this.updateTotal();
  }

  onSupplierSelect(event: any): void {
    const selectedName = this.orderForm.get('rawMaterialName')?.value;
    this.http.get<any[]>(`http://localhost:8081/raw_material/search/name/${selectedName}`)
      .subscribe(data => {
        if (data && data.length > 0) {
          const unitCost = data[0].unitCost;
          this.orderForm.patchValue({ unitPrice: unitCost });
          this.updateTotal();
        }
      });
  }

  updateTotal(): void {
    const qty = this.orderForm.get('quantity')?.value;
    const unitPrice = this.orderForm.get('unitPrice')?.value;
    if (qty && unitPrice) {
      const total = qty * unitPrice;
      this.orderForm.patchValue({ totalAmount: total });
    }
  }

  submitOrder(): void {
    if (!this.isQuantityAvailable || !this.orderForm.valid) return;

    const orderPayload = {
      order_date: this.orderForm.get('orderDate')?.value,
      status: 'In_Progress',
      total_amount: this.orderForm.get('totalAmount')?.value,
      procurement_officer_id: this.orderForm.get('procurementOfficerId')?.value
    };

    // Step 1: Create Order
    this.http.post<any>('http://localhost:8081/orders/add', orderPayload, { withCredentials: true })
      .subscribe(orderResponse => {
        this.orderId = orderResponse.orderId;
        this.raw_material=this.orderForm.get('rawMaterialName')?.value;
        // console.log("orderid - " + this.orderId);

        // Step 2: Add Order Item
        const orderItemPayload = {
          quantity: this.orderForm.get('quantity')?.value,
          unit_price: this.orderForm.get('unitPrice')?.value,
          order_id: this.orderId,
          name: this.raw_material
        };
        console.log("raw material / order item name "+this.orderForm.get('rawMaterialName')?.value);

        this.http.post('http://localhost:8081/order_items/add', orderItemPayload, { withCredentials: true })
          .subscribe({
            next: () => {
              Swal.fire({
                icon: 'success',
                title: 'Success',
                text: 'Order created successfully'
              });
              this.orderForm.reset();
              //for procurement officer dashboard purpose - notifying 
              this.OrderCreationComplete.emit();
            },
            error: (err) => {
              console.error('Order creation failed', err);
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Order creation failed'
              });
            }
          });

        // Step 3: Reduce stock quantity
        this.http.put(
          `http://localhost:8081/raw_material/${this.selectedMaterial.raw_material_id}/reduceStock/${this.orderForm.get('quantity')?.value}`,
          {},
          { withCredentials: true }
        ).subscribe({
          next: () => console.log("Stock updated successfully"),
          error: (err) => console.error("Failed to update stock", err)
        });

      });
  }
}
