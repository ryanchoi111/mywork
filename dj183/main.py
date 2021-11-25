import os
import cv2
import numpy as np
from matplotlib import pyplot as plt
from flask import Flask, request, render_template, send_from_directory, make_response
import base64
import urllib

app = Flask(__name__)

APP_ROOT = os.path.dirname(os.path.abspath(__file__))


@app.route("/")
def index():
    return render_template("mainp.html")


@app.route("/genre", methods=["GET", "POST"])
def genre():


    genre = request.form.get("genres")
    print(genre)

    return render_template("quiz.html", song_genre = genre)



@app.route('/upload/<filename>')
def send_image(filename):
    return send_from_directory("images", filename)



@app.route('/gallery')
def get_gallery():
    image_names = os.listdir('./images')
    print(image_names)
    return render_template("gallery.html", image_names=image_names)


if __name__ == "__main__":
    app.run(port=4555, debug=True)
