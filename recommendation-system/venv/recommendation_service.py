from flask import Flask, request, jsonify
import pickle
import pandas as pd
import ast

# Load the saved TF-IDF vectorizer and cosine similarity matrix
with open('tfidf_vectorizer.pkl', 'rb') as f:
    vectorizer = pickle.load(f)

with open('cosine_sim_matrix.pkl', 'rb') as f:
    cosine_sim = pickle.load(f)

# Load the menus and plats data
menus_df = pd.read_csv('menus.csv')
plats_df = pd.read_csv('plats.csv')

# Function to parse plats column correctly as a list
def parse_plats_column(plat_str):
    try:
        return ast.literal_eval(plat_str)
    except Exception as e:
        print(f"Error parsing plats: {e}")
        return []

# Apply parsing to the 'plats' column
menus_df['plats'] = menus_df['plats'].apply(parse_plats_column)

# Function to get plat names for a given list of plat ids
def get_plat_names(menu_plats):
    plat_names = []
    for plat_id in menu_plats:
        plat_name = plats_df.loc[plats_df['idPlat'] == plat_id, 'nomPlat'].values
        if plat_name.size > 0:  # Check if the array has any elements
            plat_names.append(plat_name[0])  # Append the plat name to the list
    return ' '.join(plat_names)  # Join plat names into a single string, space-separated

menus_df['features'] = menus_df['nomMenu'] + ' ' + menus_df['statut'] + ' ' + menus_df['nutritionniste'] + ' ' + menus_df['plats'].apply(get_plat_names)

# Flask app setup
app = Flask(__name__)

# Function to recommend similar menus based on cosine similarity
def recommend_menu(menu_ids, num_recommendations=1):
    recommended_menus = []

    for menu_id in menu_ids:
        if menu_id not in menus_df['idMenu'].values:
            print(f"Menu ID {menu_id} not found in the dataset.")
            continue

        idx = menus_df[menus_df['idMenu'] == menu_id].index[0]
        sim_scores = list(enumerate(cosine_sim[idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
        sim_scores = sim_scores[1:num_recommendations + 1]
        menu_indices = [i[0] for i in sim_scores]

        # 🔁 Instead of .to_dict(), manually sanitize and format
        for i in menu_indices:
            row = menus_df.iloc[i]

            recommended_menus.append({
                "idMenu": int(row["idMenu"]),
                "nomMenu": row["nomMenu"],
                "statut": row["statut"],
                "plats": row["plats"] if isinstance(row["plats"], list) else [],

                # Optional fields (if your Java DTO expects them)
                "nutritionniste": None if pd.isna(row.get("nutritionniste")) else row["nutritionniste"],
                "features": None if pd.isna(row.get("features")) else row["features"]
            })

    if not recommended_menus:
        print("No recommendations generated for the provided menu IDs.")
    
    return recommended_menus

# Route for receiving menu data and sending recommendations
@app.route('/recommend', methods=['POST'])
def recommend():
    data = request.get_json()  # Expecting a JSON with 'menus' key
    print("Received data:", data)
    menu_ids = data.get('menu_ids', [])  # List of menu IDs to get recommendations for

    if not menu_ids:
        return jsonify({"error": "No menu_ids provided"}), 400

    # Get recommendations for the list of menu IDs
    recommendations = recommend_menu(menu_ids)
    print("Recommendations response:", recommendations)

    return jsonify(recommendations)

if __name__ == '__main__':
    app.run( host='0.0.0.0', port=5000)
