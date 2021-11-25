from flask import Flask
from flask import render_template

# Creates our Flask application.
app = Flask(__name__)




# Causes the website to rerender whenever you save your file
app.config["DEBUG"] = True

# Needed for sessions to work properly. Do not worry about this.
app.config["SECRET_KEY"] = "?L=?0{yk-?t??>"
