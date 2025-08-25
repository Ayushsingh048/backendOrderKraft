import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.html',
 imports: [CommonModule,FormsModule],
})
export class ProfileSettings implements OnInit {

// Holds a temporary copy of the user's profile data (username & email).
// Used for form editing so the original loggedInUser is not modified
// until the update API call succeeds.
  editProfile = {
    username: '',
    email: ''
  };

  passwordData = {
    currentPassword: '',
    newPassword: ''
  };

 @Input() userId: number | null = null;       // passed from parent
  @Input() loggedInUser: any;        // passed from parent (username, email, etc.)
  
  activeTab: string = 'profile';
  showDropdown: boolean = false;

  errorMessage: string = '';

  constructor(private http: HttpClient,private authService: AuthService) {}

  ngOnInit(): void {
    if (this.loggedInUser) {
      this.editProfile = { ...this.loggedInUser };
    }
  }

  //  Open Settings
  showSettings() {
    this.activeTab = 'settings';
    this.showDropdown = false;
    this.editProfile = { ...this.loggedInUser };
  }

  //  Update Profile (username/email)
  updateProfile() {
    if (!this.userId) return;
    const url = `http://localhost:8081/users/update/profile/${this.userId}`;
    const payload: any = {};
    if (this.editProfile.username) payload.username = this.editProfile.username;
    if (this.editProfile.email) payload.email = this.editProfile.email;
 
    this.http.put(url, payload).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Profile Updated successfully',
          text: 'Your profile has been updated successfully.',
          confirmButtonText: 'OK',
          buttonsStyling: false,  
          customClass: {
            confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
         }
        }).then(() => {
          this.logout();  //  only after OK
        });
     
      },
      error: (err) => {
        console.error('Failed to update profile:', err);
        const errorMsg =
          typeof err.error === 'string'
            ? err.error
            : err.error?.message || 'Profile update failed. Please try again.';
      Swal.fire({
            icon: 'warning',
            title: 'Profile Update Failed',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
    }
        });
        this.errorMessage = '';
      }
    });
  }
 
  //  Update Password 
  updatePassword() {
    if (!this.userId) return;
    const url = `http://localhost:8081/users/update/password/${this.userId}`;
    const payload = {
      currentPassword: this.passwordData.currentPassword,
      newPassword: this.passwordData.newPassword
    };
 
    this.http.put(url, payload).subscribe({
      next: (res:any) => {
        Swal.fire({
          icon: 'success',
          title: 'Password Updated',
          text: res.message,   // ✅ now reads from JSON
          confirmButtonText: 'OK',
           buttonsStyling: false,
          customClass: {
            confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
         }
        }).then(() => {
          this.passwordData = { currentPassword: '', newPassword: '' };
          this.logout();  // ✅ only after OK
        });
      },
 
      error: (err) => {
        console.error('Failed to update password:', err);
        const errorMsg = err.error?.message
 
        const lowerMsg = errorMsg.toLowerCase();
 
        if (lowerMsg.includes('current password is incorrect')) {
          Swal.fire({
            icon: 'warning',
            title: 'Incorrect Password',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
            }
          });
          this.errorMessage = '';
        }
        else if (lowerMsg.includes('new password cannot be blank')) {
          Swal.fire({
            icon: 'warning',
            title: 'Invalid Password',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
            }
          });
          this.errorMessage = '';
        }
        else if (lowerMsg.includes('password must be at least')) {
          Swal.fire({
            icon: 'warning',
            title: 'Weak Password',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
            }
          });
          this.errorMessage = '';
        }
        else if (lowerMsg.includes('new password must not be the same')) {
          Swal.fire({
            icon: 'warning',
            title: 'Password Reuse Not Allowed',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
            }
          });
          this.errorMessage = '';
        }
        else {
         
          Swal.fire({
            icon: 'warning',
            title: 'Password Update Failed',
            text: errorMsg,
            confirmButtonText: 'Retry',
             buttonsStyling: false,
            customClass: {
              confirmButton: 'bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700'
            }
          });
          this.errorMessage = '';
 
        }
      }
    });
  }
  
  setActiveTab(tab: string) {
    this.activeTab = tab;
  }
  logout() {
   this.authService.logout();
  }
}
