#!/usr/bin/python3

import sys

total = 0
lastword = None
for line in sys.stdin:
    word, count = line.strip().split('\t', 1)
    count = int(count)
    if word != lastword and lastword:
        print(f"{lastword}\t{total}")
        total = 0
    lastword = word
    total = total + count
print(f"{lastword}\t{total}")
