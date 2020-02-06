select t.Score, cast(t.Rank as SIGNED) as Rank
from
(
select Score, if(Score = @t2, @t1, @t1 := @t1 + 1) as Rank, @t2 := Score
from Scores, (select @t1 := 0, @t2 := null) r
order by Score desc
) t