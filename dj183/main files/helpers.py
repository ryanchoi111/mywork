# Add more import statements as you need them!
from utility import get_four_choices, get_preview_url
import requests
import billboard
from flask import Flask, session
"""
Our dictionary to hold which genres we are giving to our users. Play around with
this dictionary to see how the starter code changes on index.html. The keys
represent the names as they appear on the website, and the values represent the
chart IDs for Billboard to use.

TODO: Add at least two more genres to this dictionary. Information on how to find
      more Billboard chart IDs is on the Billboard library documentation, as
      linked on the core page of the spec. Make sure you only choose charts that
      contain SONGS (not artists, albums, etc.).
"""
GENRES_LIST = {
    "Current Pop Hits": "hot-100",
    "Dance Club Hits": "dance-club-play-songs",
    "Country Classics": "greatest-country-songs",
    "Hip-Hop Hooray": "r-b-hip-hop-songs",
    "Holiday Jingles": "hot-holiday-songs"

}

"""
REQUIRES: a valid chart name that corresponds to a chart name on Billboard
MODIFIES: nothing
EFFECTS: chooses four random songs from the valid Billboard chart and returns
         result in a list. Uses get_four_choices() and the Billboard library.
         Remember: if you want to use a library in Python, what must we put at
         the top of the file to access its member functions?
"""
def get_four_songs(chart_name):
    chart = billboard.ChartData(chart_name)
    chartlength = chart.__len__()
    list = [0] * chartlength
    num = get_four_choices(list)
    list1 = []
    for number in num:
        song = chart[number]
        list1.append(song)

    return list1

"""
REQUIRES: nothing
MODIFIES: session
EFFECTS: returns a string with the score to print, creates key in session if necessary

If the user has answered 3 questions, 1 correctly and 2 incorrectly,
get_score should return the STRING "1/3" .

If the user has not answered any questions yet,
get_score should return the string "N/A", but NOT the string"0/0".
"""

def get_score():
    session.setdefault("topScore", default = 0)
    session.setdefault("bottomScore", default = 0)
    session.setdefault("genre", default = "")
    totalScore = ''
    if (session['bottomScore'] > 0):
        totalScore = str(session.get('topScore')) + '/' + str(session.get('bottomScore'))
    elif (session.get('topScore') == 0 and session.get('bottomScore') == 0):
        totalScore = 'N/A'
    return totalScore

"""
REQUIRES: nothing
MODIFIES: num_correct, num_total in session
EFFECTS: sets the num_correct and num_total to 0
"""
def clear_score():
    session['bottomScore'] = 0
    session['topScore'] = 0
