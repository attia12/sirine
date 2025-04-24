import { Component } from '@angular/core';
import { AbonnementService } from '../../services/abonnement.service';

@Component({
  selector: 'app-abonnements',
  templateUrl: './abonnements.component.html',
  styleUrls: ['./abonnements.component.css']
})
export class AbonnementsComponent {
  idUser!: number;
  typeAbonnement = '';
  dureeAbonnement = '';
  montant!: number;
  modePaiement = '';

  // 💡 Ajoute ces 2 lignes pour éviter les erreurs
  typeOptions = ['BASIC', 'PREMIUM', 'VIP'];
  dureeOptions = ['MENSUEL', 'ANNUEL', 'SEMESTRIEL'];

  constructor(private abonnementService: AbonnementService) {}

  ajouterAbonnement(): void {
    const dateNow = new Date();
    const dateFin = this.dureeAbonnement === 'MENSUEL'
      ? new Date(dateNow.setMonth(dateNow.getMonth() + 1))
      : this.dureeAbonnement === 'ANNUEL'
      ? new Date(dateNow.setFullYear(dateNow.getFullYear() + 1))
      : new Date(dateNow.setMonth(dateNow.getMonth() + 6)); // SEMESTRIEL

    const newAbonnement = {
      typeAbonnement: this.typeAbonnement,
      dureeAbonnement: this.dureeAbonnement,
      dateCreation: new Date(),
      dateFin: dateFin,
      montant: this.montant,
      modePaiement: this.modePaiement,
      statut: 'ACTIF',
      user: {
        idUser: this.idUser
      }
    };

    this.abonnementService.add(newAbonnement).subscribe({
      next: () => alert("✅ Abonnement ajouté avec succès !"),
      error: () => alert("❌ Erreur lors de l'ajout.")
    });
  }
}
