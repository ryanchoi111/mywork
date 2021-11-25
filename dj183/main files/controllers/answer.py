from app import app
from flask import render_template, request, send_from_directory
from flask import session
import requests
from helpers import GENRES_LIST
from helpers import get_four_songs
from helpers import get_score
from bs4 import BeautifulSoup
import re

@app.route('/answer', methods = ["GET", "POST"])
def answer():
    isTrue = False



    selected = request.form.get("song")
    answersong = request.form.get("Asong")
    answerartist = request.form.get("artist")

    answer = "'" + answersong + "'" + " by " + answerartist
    artistName = answerartist.split(" ")
    songName = answersong.split(" ")
    artistName = "+".join(artistName)
    songName = "+".join(songName)


    print("Answer: ", answer)
    print("Selected: ", selected)
    format = songName + "+" + artistName

    if (selected == answer):
        isTrue = True
        session['topScore'] = session['topScore'] + 1
        session['bottomScore'] = session['bottomScore'] + 1
    else:
        session['topScore'] = session['topScore']
        session['bottomScore'] = session['bottomScore'] + 1

    score = get_score()

    html_doc = "https://www.youtube.com/results?search_query=" + format
    r = requests.get(html_doc)
    doc = r.text
    soup = BeautifulSoup(doc, 'html.parser')
    hrefs = []
    for link in soup.find_all('a'):
        if "/watch?" in link.get('href'):
            hrefs.append(link.get('href'))

    #print(hrefs[0])
    embed_form = hrefs[0][9:]
    r1 = requests.get("https://www.youtube.com/" + "embed/" + embed_form)
    embed = "https://www.youtube.com/" + "embed/" + embed_form






    return render_template("answer.html", song_bool = isTrue, answerS = answersong, answerA = answerartist, current_score = score, embed_video = embed)
