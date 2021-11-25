from app import app
from views import *
from helpers import get_four_songs

# Acts as your "main" function in Python. Simply starts our Flask app!


if __name__ == "__main__":
    app.run(debug=True)
