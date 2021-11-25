from app import app
from flask import render_template, request, send_from_directory
from flask import session
import requests
from helpers import get_score
from firebase import firebase
# import firebase_admin
# from firebase_admin import credentials

@app.route('/leaderboard', methods = ["GET", "POST"])
def leaderboard():
    db_cursor = firebase.FirebaseApplication('https://dj183-12b13.firebaseio.com/', None)
    # cred = credentials.Certificate("/eecs183finalprojects/DJ_183_4054_Repository/dj183-12b13-firebase-adminsdk-oyiae-7e48088e5f.json")
    # firebase_admin.initialize_app(cred)
    correct_answers = {}
    top10 = []
    is_name = False
    posted = False
    score = get_score()

    if request.method == "POST":
        if score == 'N/A':
            numerator = 0
            denominator = 0
            name = request.form.get("names")
            if name:
                is_name = True
                posted = True
                db_cursor.put('/user_db', name, numerator)
            else:
                is_name = False
        else:
            score1 = score.split('/')
            numerator = int(score1[0])
            denominator = int(score1[1])
            name = request.form.get("names")
            if name:
                is_name = True
                posted = True
                db_cursor.put('/user_db', name, numerator)
            else:
                is_name = False

        ## THE CODE BELOW ACCOUNTS FOR HOLDING A USER'S CORRECT ANSWERS AND TOTAL QUESTIONS THEY ANSWERED
        # db_cursor.put('/user_db/' + name, numerator, denominator)

        # db = db_cursor.get('/user_db', None)
        # dict = db
        # dict_nums = {}
        # for keys in db.keys():
        #     db = db_cursor.get('/user_db/' + keys, None)
        #     for elem in db:
        #         dict_nums[keys] = (elem, db[elem])
        #         sort = sorted(dict_nums.items(), key = lambda t : t[0] , reverse=True)
                #this sort variable sorts by descending order of answer correct while also containing the total questions answered

        ## THE CODE BELOW ACCOUNTS FOR ONLY HOLDING A USER'S CORRECT ANSWERS

        db = db_cursor.get('/user_db', None)
        for elements in db:
            correct_answers[elements] = db[elements]

        sort_correct = sorted(correct_answers.items(), key = lambda t: t[1], reverse = True)
        top10 = sort_correct[:10]

    else:
        db = db_cursor.get('/user_db', None)
        if db is None:
            top10 = []
        else:
            for elements in db:
                correct_answers[elements] = db[elements]
            sort_correct = sorted(correct_answers.items(), key = lambda t: t[1], reverse = True)

            top10 = sort_correct[:10]




    return render_template("leaderboard.html", board_db = top10, post = posted, nameFound = is_name, current_score = score)
