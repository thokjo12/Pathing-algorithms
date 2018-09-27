import sys
import numpy as np
import matplotlib.pyplot as plt

col = 0
row = 0
readingGrid = False
readingPath = False
total = 0

name=sys.argv[1]
cost = sys.argv[2]

# read our files and start constructing the relevant data we need.
with open("src/main/python/"+name) as source:
    line = source.readline()
    col = int(line.split(',')[0])
    row = int(line.split(',')[1])
    grid = np.chararray(shape=(row, col))
    while line:
        if line.__contains__("gs"):
            line = source.readline()
            readingGrid = True
        elif line.__contains__("ge"):
            readingGrid = False
        elif line.__contains__("pe"):
            readingPath = False
        elif line.__contains__("ps"):
            line = source.readline()
            readingPath = True
        if readingGrid:
            s = list(line.rstrip())
            for i in range(len(line) - 1):
                grid[total][i] = line[i]
            gridString = line
            total += 1
        if readingPath:
            tmp_path = list(line.rstrip().split(","))
            for i in range(len(tmp_path)-1):
                xy = tmp_path[i].split(".")
                if grid[int(xy[1])][int(xy[0])] != b"B":
                    grid[int(xy[1])][int(xy[0])] = b"O"
        line = source.readline()

colors = np.zeros((row, col, 3))
fig, ax = plt.subplots()

# color our grid and put text for goal and start
for y in range(len(grid)):
    for x in range(len(grid[y])):
        if grid[y][x] == b'w':
            colors[y][x] = (0, 0, 1)
        elif grid[y][x] == b'm':
            colors[y][x] = (0.5, 0.5, 0.5)
        elif grid[y][x] == b'f':
            colors[y][x] = (0.3, 0.5, 0.3)
        elif grid[y][x] == b'g':
            colors[y][x] = (0.5, 0.8, 0.5)
        elif grid[y][x] == b'r':
            colors[y][x] = (0.639, 0.470, 0.262)
        elif grid[y][x] == b'O':
            colors[y][x] = (1, 0.5, 0.2)
        elif grid[y][x] == b'A':
            ax.text(x, y, "A", va="center", ha="center")
            colors[y][x] = (1, 0, 0)
        elif grid[y][x] == b'B':
            ax.text(x, y, "B", va="center", ha="center")
            colors[y][x] = (0, 1, 0)
        elif grid[y][x] == b'.':
            colors[y][x] = (0.9, 0.9, 0.9)
        elif grid[y][x] == b'#':
            colors[y][x] = (0.5, 0.5, 0.5)


ax.set_xticks(np.arange(-.505, col, 1))
ax.set_yticks(np.arange(-.507, col, 1))
ax.grid(color='black', linestyle='-', linewidth=1,axis='both')
ax.imshow(colors)
ax.set_title(name+ ", cost of path: " + cost)
plt.show()

