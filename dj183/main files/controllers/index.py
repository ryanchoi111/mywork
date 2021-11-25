# You might need to add more of these import statements as you implement your controllers.
from app import app
from flask import render_template, request, send_from_directory, session
from helpers import GENRES_LIST
from helpers import get_four_songs
from helpers import get_score, clear_score

@app.route('/')
def index():
    score = get_score()
    data = {
        "genres": GENRES_LIST,
    }
    return render_template("index.html", current_score = score, **data)
