package FenwickTrees;

/*
PROBLEM THIS SOLVES:

We need to support two operations efficiently on an array A[1..n]:

1) rangeUpdate(l, r, val)
   → Add "val" to every element in A[l..r]

2) rangeQuery(l, r)
   → Return the sum of A[l..r]

---

WHY NAIVE FAILS:

- Range update takes O(n)
- Range query takes O(n)

With q operations → O(n * q) ❌ too slow

---

WHAT WE WANT:

- rangeUpdate → O(log n)
- rangeQuery → O(log n)

---

KEY IDEA:

We combine:
1) Difference Array (for range updates)
2) Fenwick Tree / BIT (for fast prefix sums)

---

STEP 1: DIFFERENCE ARRAY VIEW

Let D[i] be the difference array:
    D[i] = A[i] - A[i-1]

Then:
    A[i] = D[1] + D[2] + ... + D[i]

Range update [l, r] += val becomes:
    D[l] += val
    D[r+1] -= val

---

STEP 2: PREFIX SUM WE WANT

We want:
    prefix(x) = A[1] + A[2] + ... + A[x]

Substitute A[i]:

    prefix(x) = Σ (D[1] + D[2] + ... + D[i]) for i = 1..x

Rearrange:

    prefix(x) = Σ (x - i + 1) * D[i]

Expand:

    prefix(x) = (x + 1)*ΣD[i] - Σ(i * D[i])

---

STEP 3: DATA STRUCTURE DESIGN

We maintain TWO Fenwick Trees:

BIT1 → stores D[i]
BIT2 → stores i * D[i]

So we can compute:

    prefix(x) = (x + 1)*query(BIT1, x) - query(BIT2, x)

---

OPERATIONS:

rangeUpdate(l, r, val):
    update(BIT1, l, val)
    update(BIT1, r+1, -val)

    update(BIT2, l, val * l)
    update(BIT2, r+1, -val * (r+1))

rangeQuery(l, r):
    prefix(r) - prefix(l - 1)

---

TIME COMPLEXITY:

- rangeUpdate → O(log n)
- rangeQuery → O(log n)

SPACE: O(n)

---

WHEN TO USE:

Use this when:
✔ You need BOTH range updates AND range sum queries
✔ Operations are interleaved (online queries)
✔ Difference array alone is insufficient

---

MENTAL MODEL:

Difference Array → "lazy range updates"
Fenwick Tree → "fast prefix sums"

This = BOTH combined.
*/

public class RangeUpdateRangeQueryBIT {
    long[] bit1, bit2;
    int n;

    public RangeUpdateRangeQueryBIT(int n) {
        this.n = n;
        this.bit1 = new long[n + 1];
        this.bit2 = new long[n + 1];
    }

    private void update(long[] bit, int i, long val) {
        for (; i <= n; i += i & -i) {
            bit[i] += val;
        }
    }

    public void rangeUpdate(int l, int r, long val) {
        // Update BIT1 (stores D[i])
        update(bit1, l, val);
        update(bit1, r + 1, -val);

        // Update BIT2 (stores i * D[i])
        update(bit2, l, val * l);
        update(bit2, r + 1, -val * (r + 1));
    }

    private long prefixQuery(int x) {
        long sum1 = 0, sum2 = 0;
        int i = x;

        while (i > 0) {
            sum1 += bit1[i];
            sum2 += bit2[i];
            i -= i & -i;
        }

        return sum1 * (x + 1) - sum2;
    }

    public long rangeQuery(int l, int r) {
        return prefixQuery(r) - prefixQuery(l - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        RangeUpdateRangeQueryBIT bit = new RangeUpdateRangeQueryBIT(n);

        // Operations
        bit.rangeUpdate(1, 3, 2); // [2,2,2,0,0]
        bit.rangeUpdate(2, 5, 3); // [2,5,5,3,3]

        System.out.println("Sum [1,5]: " + bit.rangeQuery(1, 5)); // 18
        System.out.println("Sum [2,4]: " + bit.rangeQuery(2, 4)); // 13
    }
}