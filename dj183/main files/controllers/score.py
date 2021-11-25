from app import app
from flask import render_template, request, send_from_directory, url_for, redirect
from helpers import GENRES_LIST, get_four_songs, get_score, clear_score

@app.route('/score', methods = ["GET",'POST'])
def score():
    score = get_score()
    if request.method == "GET":
        return render_template("score.html", current_score = score)

    elif request.method == "POST":
        answer = request.form.get("answer")
        if answer == "yes":
            clear_score()
            return redirect(url_for('index'))
        elif answer == "no":
            return redirect(url_for('index'))
        elif answer == None:
            return redirect(url_for('index'))
