# HashCode 2019 - Pizza - Practice Round

![](https://anchr.io/i/hkAyX.png)

This is my solution for the [Google Hash Code](https://hashcode.withgoogle.com) 2019 practice round. There's plenty of room to optimise, but the aim of the competition is to get an efficient answer quickly. My answer is by no means optimal, but it's simple and efficient logic and easy to understand the code. For a full problem statement, click [here (.pdf)](https://github.com/ellisvlad/hashcode2019-pizza/raw/master/pizza.pdf).

## Usage
`java -cp . Main inputs/d_big.in`

## Approach
My first idea was to work through the grid of pizza focusing on one dimention at a time and looking at all possible cuts. I filtered out those which did not satisfy the problem's constraints, and then checked for an optimal solution with the results. This worked great for the example round, but any larger pizza made the program take a long time to run, as the method was tedious and complicated.

My original idea hit a dead end, so I got to thinking, why not just try to remove as many large slices of pizza as I could find, then work my way down to smaller and smaller pizza slices.

After a little while my second plan was implemented, and that's what you see here. The program first makes a list of possible sizes of pizza slice, then works through the largest to smallest size slices, attempting to take cutouts of each slice size from the pizza. This led to a pretty efficient and well working program that gave good results.

## Potential Future Work
* This method can be parallelised efficiently with correct locking in place
* Looking for pizza slices in a different order may be more efficient

## Scores
| Input  | Runtime  |  Slices  | Points |
|  :---  |  :----:  |  :----:  |  ---: |
| Small  |   20ms   |    6     | **24** |
| Medium |   100ms  |   3,794  | **45,477** |
| Big    |   850ms  |   57,776 | **807,527** |

Running time is all subject to your hardware, of course!
