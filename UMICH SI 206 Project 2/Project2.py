from bs4 import BeautifulSoup
import requests
import re
import os
import csv
import unittest


def get_headlines_from_search_results(filename):
    """
    Write a function that creates a BeautifulSoup object on the passed filename. Parse
    through the object and return a list of headlines for each search result in the
    following format:

    ['Headline 1', 'Headline 2'...]
    """
    headline_list = []
    with open(filename, "r") as f1:

        contents = f1.read()
        
        soup = BeautifulSoup(contents, "lxml")

        headlines = soup.find_all("h3", class_ = "search-result-title-back search-results-headline")
        for stuff in headlines:
            headline_list.append(stuff.get_text())
    return headline_list


def get_most_recent():
    """
    Write a function that creates a BeautifulSoup object after retrieving content from
    "https://www.freep.com". Parse through the object and return a list of URLs for each
    of the articles in the "MOST RECENT" section using the following format:

    ['https://www.fre
    ep.com/story/news/local/michigan/2020/02/26/michigan-foia-slow-costly-whitmer-transparency/4786653002/', ...]
    """
    most_recent_list = []
    base_url = "https://www.freep.com"
    r = requests.get(base_url)
    soup = BeautifulSoup(r.text, 'lxml')

    most_recent = soup.find_all("div", class_="flh3m-feed-item")
    for vals in most_recent:
        most_recent_list.append(base_url + vals.a.get('href'))
        #most_recent_list.append(base_url+ vals.get('href'))
    return most_recent_list


def get_article_summary(article_url):
    """
    Write a function that creates a BeautifulSoup object that extracts information 
    from an article, given the relative URL of the article. parse through
    the BeautifulSoup object, and capture the article title, author, and date. Make
    sure to strip() any whitespace from the date. If the timestamp contains
    "Published" and "Updated" information, you should only capture the "Published"
    part.

    This function should return a list of tuples in the following format:

    [('Some headline', 'Some Author', 'Published 12:00 a.m. ET Jan 01, 2020')...]

    HINT: Using BeautifulSoup's find() method may help you here.
    You can easily capture CSS selectors with your browser's inspector window.
    """
    list1 = []
    base_url = article_url
    r = requests.get(base_url)
    soup = BeautifulSoup(r.text, 'lxml')

    headl = soup.find("h1", class_="asset-headline speakable-headline")
    headline = headl.get_text()
    author = soup.find("div", class_= "asset-metabar")
    auth = author.a.get_text()
    date = soup.find("span", class_= "asset-metabar-time asset-metabar-item nobyline")
    stripped = date.get_text().strip()
    
    if "Updated" in stripped:
        split = stripped.split("|")
        published = split[0]
        date = published.strip()
    else:
        date = stripped.strip()
    
    list1.append((headline, auth, date))
    
    return list1


def summarize_corrections(filepath):
    """
    Write a function to get the section tags and dates of the Corrections &
    Clarifications article in "corrections.htm". You need to use regex to accomplish
    this. This function should create a BeautifulSoup object from a filepath and
    return a list of (tag, date) tuples.

    For example, if the article contains: "Sports: Jan 1, a basketball story was
    Published", you should append ("Sports", "Jan 1") to your list of tuples.
    """

    with open(filepath, "r") as f1:

        contents = f1.read()
        soup = BeautifulSoup(contents, "lxml")
        tagsanddates = soup.find_all('em')
        combined = []
        for stuff in tagsanddates:
            dates = re.findall(r":(?:\sIn\s|\s)?(?:the|[aA]|(?:A|a)n)? ((?:(?:\w{3,8})|(?:\w{3}\.))\s(?:\d{1,2}))", stuff.get_text())
            tags = re.findall(r"([A-Z]{1}(?:[a-z])+):(?:&nbsp;|\s)?", stuff.get_text())
            combined.append((tags[0], dates[0]))
        return combined
        


def write_csv(data, filename):
    """
    Write a function that takes in a list of tuples called data
    (i.e. the one that is returned by summarize_corrections()),
    writes the data to a csv file, and saves it to the passed filename.

    The first row of the csv should contain "Tag" and
    "Date" respectively. For each tuple in data, write a new row to
    the csv, placing each element of the tuple in the correct column.

    When you are done your CSV file should look like this:

    Tag,Date
    Some Tag, Jan. 1
    Another Tag, Feb. 2
    Yet another Tag, Mar. 3


    This function should not return anything.
    """
    direct = os.path.dirname(__file__)
    f = open(os.path.join(direct, filename), 'w')
    csv_writer = csv.writer(f, delimiter = ',', quotechar = '"', quoting = csv.QUOTE_MINIMAL)
    csv_writer.writerow(['Tag', 'Date'])
    for tags, date in data:
        csv_writer.writerow([tags, date])
    f.close()

    



# def summarize_corrections_expanded(filepath):
#     """
#     EXTRA CREDIT – Uncomment if you wish to attempt.
#
#     Please see the instructions document for more information on how to complete this function.
#     You do not have to write test cases for this function.
#     """
#
#     pass


class TestCases(unittest.TestCase):


    # Create a class variable and store the most recent article url list in it.
    # This can be accessed using TestCases.article_url_list in the test functions
    article_url_list = get_most_recent()
    
    #main1 = os.path.join(file1, "search.htm")

    def test_get_headlines_from_search_results(self):
        file1 = os.path.dirname(__file__)
        headlines = get_headlines_from_search_results(os.path.join(file1, "search.htm"))
        self.assertEqual(len(headlines), 10)
        self.assertIs(type(headlines), list)
        for each in headlines:
            self.assertIs(type(each), str)
        self.assertEqual(headlines[0], "Photos: Michigan 4, Michigan State 1")
        self.assertEqual(headlines[len(headlines)-1], "U-M basketball makes statement with win at Rutgers")
        
        # call get_headlines_from_search_results("search.htm") and save the result 
        # check that the number of headlines extracted is correct (10 headlines)
        # check that the variable you saved after calling the function is a list
        # check that each headline in the list is a string
        # check that the first headline is correct (open search.htm and find it)
        # check that the last headline is correct (open search.htm and find it)
        

    def test_get_most_recent(self):
        self.assertIs(type(TestCases.article_url_list), list)
        self.assertEqual(len(TestCases.article_url_list),6)
        for links in TestCases.article_url_list:
            self.assertIs(type(links), str)
            self.assertTrue(links.startswith("https://www.freep.com/story/"))
        
        # check that TestCases.article_url_list is a list
        # check that the length of TestCases.article_url_list is correct (6 URLs)
        # check that each URL in the TestCases.article_url_list is a string
        # check that each URL contains the correct url for the Detroit Free Press followed by /story/
        

    def test_get_article_summary(self):
        final = []
        for things in TestCases.article_url_list:
            final.extend(get_article_summary(things))
        self.assertEqual(len(final), 6)
        for summary in final:
            self.assertIs(type(summary), tuple)
            self.assertEqual(len(summary), 3)
            self.assertIs(type(summary[0]), str)
            self.assertIs(type(summary[1]), str)
            self.assertIs(type(summary[2]), str)
            self.assertIn("Published", summary[2])
    	# create a local variable – summaries – a list that contains the results from 
    	# get_article_summary() for each URL in TestCases.article_url_list

        # check that the number of article summaries is correct (6)
            # check that each item in the list is a tuple
            # check that each tuple has 3 elements
            # check that each element is a string
            # check that each date contains the word "Published"

    def test_summarize_corrections(self):
        file1 = os.path.dirname(__file__)
        main2 = os.path.join(file1, "corrections.htm")
        sum_correct = summarize_corrections(main2)
        self.assertEqual(len(sum_correct), 37)
        for items in sum_correct:
            self.assertIs(type(items), tuple)
            self.assertEqual(len(items), 2)
        
        self.assertEqual(sum_correct[0][0], "Life")
        self.assertEqual(sum_correct[0][1], "July 1")
        self.assertEqual(sum_correct[len(sum_correct)-1][0], "Opinion")
        self.assertEqual(sum_correct[len(sum_correct)-1][1], "Jan. 26")

        # call summarize corrections and save the results to a variable
        # check that we have the right number of corrections (37)
            # assert each item in the list of corrections is a tuple
            # check that each tuple has a length of 2
        # check that the first tuple is made up of the following 2 strings: "Life" and "July 1"
        # check that the last tuple is made up of the following 2 strings: "Opinion" and "Jan. 26"

    def test_write_csv(self):
        file1 = os.path.dirname(__file__)
        main2 = os.path.join(file1, "corrections.htm")
        test = summarize_corrections(main2)
        write_csv(test, "testcheck.csv")
        test = os.path.join(file1, "testcheck.csv")

        with open(test, 'r', newline='') as csvfile:
            reader = csv.reader(csvfile)
            l1 = list(reader)
            self.assertEqual(len(l1), 38)
            self.assertEqual(l1[0][0], "Tag")
            self.assertEqual(l1[0][1], "Date")
            self.assertEqual(l1[1][0], "Life")
            self.assertEqual(l1[1][1], "July 1")
            
            self.assertEqual(l1[len(l1)-1][0], "Opinion")
            self.assertEqual(l1[len(l1)-1][1], "Jan. 26")
            

        # call summarize_corrections on "corrections.htm" and save the result to a variable
        # call write csv on the variable you saved
        # read in the csv that you wrote

        # check that there are 38 lines in the csv
        # check that the header row is correct
        # check that the next row is "Life,July 1"
        # check that the last row is "Opinion,Jan. 26"


if __name__ == '__main__':
    unittest.main(verbosity=2)
    file1 = os.path.dirname(__file__)
    main1 = os.path.join(file1, "search.htm")
    main2 = os.path.join(file1, "corrections.htm")
    #print(get_headlines_from_search_results(main1))
    #print(get_most_recent())
    #print(get_article_summary("https://www.freep.com/story/sports/nfl/lions/2020/03/17/detroit-lions-nicholas-williams-nfl-free-agency/5065706002/"))
    #print(summarize_corrections(main2))
    #list1 = [("Sports", "Jan. 1"), ("Lifestyle", "Mar. 4"), ("News", "Dec. 12")]
    #write_csv(summarize_corrections(main2), "test.csv")
    # c = os.path.join(file1, "test.csv")
    # with open (c, 'r', newline='') as csvfile:
    #     reader = csv.reader(csvfile)
    #     l1 = list(reader)
    #     print(l1[0][0])        
        
