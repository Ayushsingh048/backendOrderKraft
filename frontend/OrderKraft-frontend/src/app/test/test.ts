import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [RouterModule, FormsModule,ReactiveFormsModule,HttpClientModule],
  templateUrl: './test.html',
  styleUrls: ['./test.css']
})
export class Test {
 refundOrder: string = "";
//refund function
getRefund() {
  this.http.post<string>(`http://localhost:8081/payments/refund/${this.refundOrder}`, {})
    .subscribe({
      next: (res) => {
        console.log("Refund Success:", res);
        alert(res);
      },
      error: (err) => {
        console.error("Refund Failed:", err);
        alert("Refund failed!");
      }
    });
}

oid: any;
private baseUrl = "http://localhost:8081/payments/check";
//get status
getStatus() {
  const url = `${this.baseUrl}?orderid=${this.oid}`;
  this.http.get(url, { withCredentials: true }).subscribe({
    next: (res) => {
      console.log("Response:", res);
    },
    error: (err) => {
      console.error("Error fetching status:", err);
    }
  });
}

  payment: FormGroup;
  private router = inject(Router); //  Inject once at top
  private url = "http://localhost:8081/payments/checkout";
  private payurl="http://localhost:8081/payments/add";
  public orderid:String='';
  
  constructor(private fb: FormBuilder, private http: HttpClient){
    this.payment = this.fb.group({
      name: ['', [Validators.required ]],
      quantity: ['', Validators.required],
      amount:['',Validators.required]
    });
    
  }
  logout(): void {
    localStorage.removeItem('authToken');
    console.log("token deleted");
    this.router.navigate(['/login']);
  }
  onSubmit(): void{
    const details = this.payment.value;
    console.log(details); 
    const now = new Date();
    this.http.post<any>(this.url,details,{withCredentials:true}).subscribe({
      next:(response)=>{
        console.log(response);
        // this.paymentDTO.order_id=this.orderid;
        this.http.post(this.payurl,{'order_id':this.orderid,
                                    'session_id':response.sessionId,
                                    'amount':details.amount,
                                    'payment_date':'',
                                    'method':'',
                                    'status':'Initiated',
        },{withCredentials:true}).subscribe({
          next:(res)=>{
            console.log("into the 2nd api");
             window.location.href = response.sessionUrl;
          }
        });
        
      }
    })
  }
}
