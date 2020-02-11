This program first merges all xml files in pubmeddata folder, the merged output is outFile.txt which will be used
by searchKeyword() method in the main class. This program will firstly check if an outFile.txt exists. As merging
files can be an expensive operation, if files are already merged, the merge method will not be run every single
time. However, if the data files have changed, the outFile.txt will need to be deleted in order to re-merge.

The search function use two scanners to go through the file. currentLine is used to determine whether an article
has the keyword that is searched in either <ArticleTitle>, </DescriptorName>, </QualifierName>, or </Keyword>. If
ti's a hit, second scanner will print out desired information in order. The search history is also stored. User
inputs are stored in a hashmap, when displaySearchHistory method is called, search keyword, time(s) searched, and
timestamps are printed out. 

As this file is very large, and it is possible a search will yield thousands of results. The results are divided
into sections of 20. If more results are needed, input anything in the console, for stop, type "N".