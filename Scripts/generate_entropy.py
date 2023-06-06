from pathlib import Path
from nltk.lm.preprocessing import flatten
from nltk.lm.preprocessing import pad_both_ends
from nltk.util import everygrams
from nltk.lm.preprocessing import padded_everygram_pipeline
from nltk.lm import MLE
import re
import csv
import os
from nltk.util import pad_sequence

n = 2

# --- Reading files into a list of lists of tokens ----
# Each line of code becomes a list of tokens appended to token_list
input_dir = '../Data/Preprocessed/'
filecount = 0
token_list = []

print("Getting tokens from files")
for filename in os.listdir(input_dir):
    #print("Getting tokens from " + filename)
    filecount += 1

    if filecount % 1000 == 0:
        print("Files complete: " + str(filecount))

    code_file = open(os.path.join(input_dir, filename), 'r', encoding="utf8")
    code_lines = code_file.readlines()

    for line in code_lines:
        # https://stackoverflow.com/questions/56550620/how-to-create-a-regex-for-tokenizing-java-source-code-in-python
        tokens = re.findall(r"\w+(?:'\w+)*|[^\w\s]", line)
        token_list.append(tokens)

print("Complete at " + str(filecount) + " files\n")
# print(token_list)


# ---- Training ----
print("Training")

# Add <s> and </s> tokens to each list
token_list = [list(pad_sequence(t_list, pad_left=True, left_pad_symbol="<s>",
                                pad_right=True, right_pad_symbol="</s>", n=2)) for t_list in token_list]

# print(token_list)
# print()

# Flattens lists of tokens into one list with all tokens
# Vocab = list of all tokens
vocab = [item for sublist in token_list for item in sublist]

# Splits the unflattened token lists into ngrams of min and max size
token_list = [list(everygrams(t_list, 1, n)) for t_list in token_list]

# Prints all ngrams
# for t_list in token_list:
#     for t in t_list:
#         print(t)
#     print()
# print()

# Train = list of list of ngrams, each line of code is a list of ngrams for it
train = token_list

# Training
lm = MLE(n)

lm.fit(train, vocab)
print(lm.vocab)
print(lm.counts)
print()


# ---- Testing ----
print("Testing")
#input_dir = '../Data/Preprocessed/'
input_dir = '../Data/TestFiles/'
output_dir = '../Data/Output/'

# Create output folder
Path("../Data/Output/").mkdir(parents=True, exist_ok=True)

for root, dirs, files in os.walk(input_dir):
    for filename in files:
        #print("Getting entropy for " + filename)
        # code_file = open(os.path.join(input_dir, filename),
        #                  'r', encoding="utf8")

        input_file = os.path.join(root, filename)
        code_file = open(input_file, 'r', encoding="utf8")
        code_lines = code_file.readlines()
        # print(input_file)

        output_file = output_dir + os.path.join(os.path.basename(
            root), filename.replace('.java', '-entropy.csv'))

        # print(output_file)

        if not os.path.exists(output_dir + os.path.basename(root)):
            os.makedirs(output_dir + os.path.basename(root))

        # Create csv header
        with open(output_file, 'w', newline='', encoding="utf8") as csvfile:
            csvwriter = csv.writer(csvfile)
            csvwriter.writerow(['line_of_code', 'entropy'])

        for line in code_lines:
            # https://stackoverflow.com/questions/56550620/how-to-create-a-regex-for-tokenizing-java-source-code-in-python
            tokens = re.findall(r"\w+(?:'\w+)*|[^\w\s]", line)

            # Gets all 1grams and 2grams for each line
            padded_bigrams = list(pad_both_ends(tokens, n=2))
            test = list(everygrams(padded_bigrams, max_len=2))

            with open(output_file, 'a', newline='', encoding="utf8") as csvfile:
                csvwriter = csv.writer(csvfile)
                csvwriter.writerow([line.rstrip(), str(lm.entropy(test))])
