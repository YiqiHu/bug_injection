from pathlib import Path
import os
import re

# https://stackoverflow.com/questions/241327/remove-c-and-c-comments-using-python


def comment_remover(text):
    def replacer(match):
        s = match.group(0)
        if s.startswith('/'):
            return " "  # note: a space and not an empty string
        else:
            return s
    pattern = re.compile(
        r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
        re.DOTALL | re.MULTILINE
    )
    return re.sub(pattern, replacer, text)


def delete_blanks(code):
    return os.linesep.join([s for s in code.splitlines() if s.strip()])


# ----- Preprocess snapshot files ----
input_dir = '../Data/Snapshots/'
output_dir = '../Data/Preprocessed/'

# Create output folder
Path("../Data/Preprocessed/").mkdir(parents=True, exist_ok=True)

for root, dirs, files in os.walk('../Data/Snapshots/'):
    for name in files:
        if '.java' not in name:
            continue

        #print("File: " + os.path.join(root, name))

        in_file = open(os.path.join(root, name), 'r', encoding="utf8")

        #print("Preprocessing " + name)

        path = (os.path.join(root, name))

        # Title appends 'hadoop-010120' to head of output file name
        title = re.search('/Snapshots/(.*)', path)
        title = title.group(1).partition('\\')[0]

        code = in_file.read()
        code = comment_remover(code)
        code = delete_blanks(code)

        out_file_name = output_dir + title + '-' + \
            name.replace('.java', '-preprocessed') + '.java'
        # print(out_file_name)

        # Add 010120 to output name so it doesn't overwrite others
        out_file = open(out_file_name, 'w', newline='', encoding="utf8")
        out_file.write(code)
