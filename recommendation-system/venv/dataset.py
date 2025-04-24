import pandas as pd
import numpy as np
from random import choice, randint
from datetime import datetime, timedelta

# Define the enums for the Menu and Plat
statut_menu = ['EN_ATTENTE', 'VALIDE', 'REJETE']
statut_plat_options = ['CONFIRME', 'ANNULE']  # Renamed to avoid conflict
type_plat = ['ENTREE', 'PLAT_PRINCIPAL', 'DESSERT']
type_regime = ['VEGAN', 'VEGETARIEN', 'SANS_GLUTEN', 'SANS_LACTOSE', 'HYPERPROTEINE', 'CLASSIQUE']

# Create synthetic data for Menus
def create_menu_data(num_menus=100):
    menus = []
    for _ in range(num_menus):
        id_menu = randint(1, 1000)
        nom_menu = f"Menu_{id_menu}"
        date_debut = datetime.now() - timedelta(days=randint(1, 30))
        date_fin = date_debut + timedelta(days=randint(1, 10))
        statut = choice(statut_menu)
        confirme = choice([True, False])
        nutritionniste = f"Nutritionniste_{randint(1, 20)}"  # Simulated user ID for nutritionist
        plats = [f"Plat_{randint(1, 50)}" for _ in range(randint(1, 5))]  # Menu associated with Plat IDs
        
        menus.append({
            'idMenu': id_menu,
            'nomMenu': nom_menu,
            'dateDebut': date_debut.strftime('%Y-%m-%d'),
            'dateFin': date_fin.strftime('%Y-%m-%d'),
            'statut': statut,
            'confirme': confirme,
            'nutritionniste': nutritionniste,
            'plats': plats
        })
    
    return pd.DataFrame(menus)

# Create synthetic data for Plats
def create_plat_data(num_plats=100):
    plats = []
    for _ in range(num_plats):
        id_plat = randint(1, 1000)
        nom_plat = f"Plat_{id_plat}"
        type_plat_val = choice(type_plat)
        calories = randint(100, 800)
        regime = choice(type_regime)
        allergenes = f"Allergenes_{randint(1, 5)}"
        statut_plat = choice(statut_plat_options)  # Using the renamed variable
        
        plats.append({
            'idPlat': id_plat,
            'nomPlat': nom_plat,
            'typePlat': type_plat_val,
            'calories': calories,
            'regime': regime,
            'allergenes': allergenes,
            'statutPlat': statut_plat
        })
    
    return pd.DataFrame(plats)

# Create the dataframes for menus and plats
menus_df = create_menu_data()
plats_df = create_plat_data()

# Saving the datasets to CSV
menus_df.to_csv('menus.csv', index=False)
plats_df.to_csv('plats.csv', index=False)

# Show the first few rows of both datasets
print(menus_df.head())
print(plats_df.head())
