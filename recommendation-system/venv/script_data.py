import mysql.connector
from datetime import date, timedelta
import random

# Connect to MySQL
conn = mysql.connector.connect(
    host="localhost",
    user="root",            # Change as needed
    password="",            # Change as needed
    database="PidevFFF"
)

cursor = conn.cursor()

# Step 1: Insert 10 plats
type_plats = ["ENTREE", "PLAT_PRINCIPAL", "DESSERT"]
regimes = ["VEGETARIEN", "CLASSIQUE", "VEGAN", "SANS_GLUTEN"]
plats_data = []

for i in range(10):
    plats_data.append((
        f"Plat Test {i+1}",
        random.choice(type_plats),
        random.randint(100, 600),
        random.choice(regimes),
        "aucun",
        "CONFIRME"
    ))

for plat in plats_data:
    cursor.execute("""
        INSERT INTO plat (nom_plat, type_plat, calories, regime, allergenes, statut_plat)
        VALUES (%s, %s, %s, %s, %s, %s)
    """, plat)

# Get inserted Plat IDs
cursor.execute("SELECT id_plat FROM plat ORDER BY id_plat DESC LIMIT 10")
plat_ids = [row[0] for row in cursor.fetchall()]

# Step 2: Insert 20 menus
statuts = ["VALIDE", "EN_ATTENTE", "REJETE"]

for i in range(20):
    nom_menu = f"Menu Test {i+1}"
    date_debut = date(2025, 4, 1) + timedelta(days=i)
    date_fin = date_debut + timedelta(days=7)
    statut = random.choice(statuts)

    cursor.execute("""
        INSERT INTO menu (nom_menu, date_debut, date_fin, statut, confirme, id_nutritionniste)
        VALUES (%s, %s, %s, %s, %s, NULL)
    """, (nom_menu, date_debut, date_fin, statut, True))

    menu_id = cursor.lastrowid

    # Randomly assign 3 to 6 plats per menu
    assigned_plats = random.sample(plat_ids, k=random.randint(3, 6))
    for plat_id in assigned_plats:
        cursor.execute("INSERT INTO menu_plat (menu_id, plat_id) VALUES (%s, %s)", (menu_id, plat_id))

# Commit and close
conn.commit()
cursor.close()
conn.close()
