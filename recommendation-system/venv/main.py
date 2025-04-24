import requests
import pandas as pd

# Fetch from Spring Boot
response = requests.get("http://localhost:8080/Menu/api/recommendations/training-data")
menus = response.json()

# Convert to DataFrame
menus_df = pd.DataFrame(menus)

# Convert plats to string features
menus_df['plats'] = menus_df['plats'].apply(lambda plats: ' '.join(plats))
menus_df['features'] = menus_df['nomMenu'] + ' ' + menus_df['statut'] + ' ' + menus_df['nutritionniste'] + ' ' + menus_df['plats']

# TF-IDF and cosine similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pickle

vectorizer = TfidfVectorizer(stop_words='english')
tfidf_matrix = vectorizer.fit_transform(menus_df['features'])
cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

# Save trained model
with open('tfidf_vectorizer.pkl', 'wb') as f:
    pickle.dump(vectorizer, f)
with open('cosine_sim_matrix.pkl', 'wb') as f:
    pickle.dump(cosine_sim, f)

# Optionally save DataFrame for Flask
menus_df.to_csv('menus.csv', index=False)
