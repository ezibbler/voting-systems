# voting-systems
A Java-based test of voting systems and how elections would differ if alternate voting systems were used

## Methodology
The program creates 100,000 simulated voters with a 16-dimensional vector representing their political beliefs (See: [Spatial Voting][spatial]) and 8 possible candidates, each with their own belief vectors. The distance between these vectors is used to determine which candidate each voter will cast their vote for.

## List of Voting Systems Used
* [First Past the Post][fptp]
* [Ranked-Choice Voting][rcv]
* [Borda Count][borda]
* [Two-round system][two-round]

## Other Sourced Info
* List of 1000 most common last names sourced from [craigh411's List of the 1000 Most Common Last Names][names]

[names]: https://gist.github.com/craigh411/19a4479b289ae6c3f6edb95152214efc
[fptp]: https://en.wikipedia.org/wiki/First-past-the-post_voting
[rcv]: https://en.wikipedia.org/wiki/Instant-runoff_voting
[borda]: https://en.wikipedia.org/wiki/Borda_count
[spatial]: https://en.wikipedia.org/wiki/Spatial_voting
[two-round]: https://en.wikipedia.org/wiki/Two-round_system