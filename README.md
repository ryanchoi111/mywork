# Introduction
What if there was a way to identify the author of an anonymous text? In this project, we will build an infrastructure for analyzing texts so they can be compared for similarity.

Data scientists have successfully achieved this by comparing the frequency of common words in an author’s writings. These frequencies form a descriptor of an author’s style, which tends to stay constant across their works. We can compare the frequencies of different writings, to see which writings are similar.

This method was able to identify “Robert Galbraith” as the pen name of JK Rowling (note the overlap of the light blue and dark blue dots in the graph).

(To try different word combinations on this graph, see [Can You Identify an Author By How Often They Use the Word “The”?](identifying_an_author_s_prose_can_be_as_simple_as_counting_how_much_they))

# In this project...
In this project, you will develop a TextAnalyzer class. A TextAnalyzer object will read in a file and do all of the analysis needed to create the frequency “fingerprint” for that text.

Here’s an example of how the TextAnalyzer works, using a very short text:

```
>>> fightsong = TextAnalyzer("files_for_testing/fightsong.txt")
>>> fightsong.line_count()
2
>>> fightsong.word_count()
9
>>> fightsong.vocabulary()
['champions', 'hail', 'michigan', 'of', 'the', 'to', 'west']
>>> fightsong.frequencies()
{'hail': 2, 'to': 1, 'michigan': 1, 'the': 2, 'champions': 1, 'of': 1, 'west': 1}
>>> fightsong.frequency_of('champions')
1
>>> fightsong.frequency_of('ohio')
0
>>> fightsong.most_common()
'hail'
>>> fightsong.most_common(3)
['hail', 'the', 'champions']
>>> fightsong.percent_frequencies()
{'hail': 0.2222222222222222, 'to': 0.1111111111111111, 'michigan': 0.1111111111111111, 'the': 0.2222222222222222, 'champions': 0.1111111111111111, 'of': 0.1111111111111111, 'west': 0.1111111111111111}
```

# The code...
Please see the instructions PDF or the docstrings for the list of methods you need to implement.

# Grading
There are unit tests included in the stub code that test each method. We will use the same tests that we provide to you in order to calculate your final grade.

Please see the table in the instructions PDF for the point value breakdown of each method.

If all of the unit tests for a method pass, you get all of the points for that method! If only some of the tests pass, you get a fraction of the points for that method. For example, if 2 out of 3 tests related to line_count() pass, then you get ⅔ of the possible points for line_count (8 points out of 12 points).

# Tips
Work on one method at a time. Choose the one that you think is the easiest, and work on it until you can get all the tests related to that method to pass. This is a great strategy, since **the solution to some methods can be used to quickly complete other methods.**

**Make sure you are using Python 3!!** Some of the tests won’t pass if you are using Python 2.

# Extra Credit
Please see the instructions PDF for more details.
