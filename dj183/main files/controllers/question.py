from app import app
from flask import render_template, request, send_from_directory, session
from helpers import GENRES_LIST, get_four_songs, get_score
from utility import get_preview_url
import billboard
import random

@app.route('/question', methods = ["GET", "POST"])
def question():
    if (request.form.get("genres") is None):
        fourSongs = get_four_songs(session['genre'])
    else:
        genre = request.form.get("genres")
        session['genre'] = genre
        fourSongs = get_four_songs(genre)
    errors = False
    previewdictkeys = []
    keys = []

    for num in fourSongs:
        previewdictkeys.append(get_preview_url(num.title, num.artist).keys())

    for element in previewdictkeys:
        for dictK in element:
            keys.append(dictK)


    while ("error" in keys):
        errors = True
        #the code below will call another set of four songs and check the returned json dictionary for an error
        for num in fourSongs:
            previewdictkeys.append(get_preview_url(num.title, num.artist).keys())
        for element in previewdictkeys:
            for dictK in element:
                keys.append(dictK)


    randomnum = random.randint(0, len(fourSongs) - 1)
    songpreview = get_preview_url(fourSongs[randomnum].title, fourSongs[randomnum].artist)
    answersong = fourSongs[randomnum].title
    answerartist = fourSongs[randomnum].artist
    previewurl = songpreview["url"]
    score = get_score()

    return render_template("question.html", song_genre = session['genre'], preview = previewurl, song_options = fourSongs, song_name = answersong, song_artist = answerartist, current_score = score)
