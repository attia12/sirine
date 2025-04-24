import {Component, OnInit, ViewChild} from '@angular/core';
import {PlatService} from "./service/plat.service";
import {MenuService} from "../menu/menu.service";
import Swal from "sweetalert2";
declare var bootstrap: any;
@Component({
  selector: 'app-plat',
  templateUrl: './plat.component.html',
  styleUrls: ['./plat.component.css']
})
export class PlatComponent implements OnInit{
  @ViewChild('platModal', { static: false }) platModal: any;

  plats: any[] = [];
  menus: any[] = [];

  platForm: any = {
    nomPlat: '',
    calories: 0,
    regime: 'VEGETARIEN',
    allergenes: '',
    statutPlat: 'CONFIRME',
    typePlat: 'PLAT_PRINCIPAL',
    menuIds: []
  };

  isEditMode: boolean = false;
  currentPlatId: number = 0;
  plat:any;


  constructor(
    private platService: PlatService,
    private menuService: MenuService
  ) {}

  ngOnInit(): void {
    this.loadPlats();
    this.loadMenus();
  }

  // Load all plats
  loadPlats(): void {
    this.platService.getAllPlats().subscribe(
      (data) => {
        this.plats = data;
      },
      (error) => console.error(error)
    );
  }

  // Load all menus for association
  loadMenus(): void {
    this.menuService.getAllMenus().subscribe(
      (data) => {
        this.menus = data;
      },
      (error) => console.error(error)
    );
  }

  // Open modal in add mode
  openAddPlatModal(): void {
    this.isEditMode = false;
    this.resetForm();
    const modalElement = this.platModal.nativeElement;  // Get the modal DOM element
    const modal = new bootstrap.Modal(modalElement);  // Create a Bootstrap modal instance
    modal.show();  // Open the modal
  }

  // Open modal in edit mode
  openEditPlatModal(plat: any): void {
    this.currentPlatId = plat.idPlat;
    console.log('openEditMode triggered for plat', plat);  // Debugging line
    this.isEditMode = true;


    if (typeof plat.menuIds === 'string') {
      plat.menuIds = plat.menuIds.split(',').map((id: any) => parseInt(id, 10)); // Assuming a comma-separated string
    }

    this.platForm = { ...plat };
    const modalElement = this.platModal.nativeElement;
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
  }

  // Add new plat
  addNewPlat(): void {
    this.platService.addPlat(this.platForm).subscribe(
      (response) => {
        this.plats.push(response);
        this.resetForm();
        this.closeModal();
      },
      (error) => console.error(error)
    );
  }

  // Update existing plat
  updatePlat(): void {
    this.platService.updatePlat(this.currentPlatId, this.platForm).subscribe(
      (response) => {
        const index = this.plats.findIndex((plat) => plat.idPlat === this.currentPlatId);
        if (index !== -1) {
          this.plats[index] = response;
        }
        this.resetForm();
        this.closeModal();
      },
      (error) => console.error(error)
    );
  }


  resetForm(): void {
    this.platForm = {
      nomPlat: '',
      calories: 0,
      regime: 'VEGETARIEN',
      allergenes: '',
      statutPlat: 'CONFIRME',
      typePlat: 'PLAT_PRINCIPAL',
      menuIds: []
    };
  }

  // Close the modal
  closeModal(): void {
    const modalElement = this.platModal.nativeElement;
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    modalInstance.hide();


    const backdropElement = document.querySelector('.modal-backdrop');
    if (backdropElement) {
      backdropElement.remove();
    }
  }

  deletePlat(platId: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This plat will be permanently deleted!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.platService.deletePlat(platId).subscribe(
          () => {
            // Successfully deleted, remove plat from the list
            this.plats = this.plats.filter(plat => plat.idPlat !== platId);
            Swal.fire('Deleted!', 'Your plat has been deleted.', 'success');  // Success alert
          },
          error => {
            // Handle error
            console.error(error);
            Swal.fire('Error!', 'There was an issue deleting the plat.', 'error');  // Error alert
          }
        );
      }
    });
  }

}
