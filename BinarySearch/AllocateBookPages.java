package BinarySearch;
/**You are given:

pages[] → number of pages in each book (in order)

students → number of students

Constraints:

Each student must get contiguous books.

Each book must be assigned to exactly one student.

Each student can get at least one book.

Goal: Minimize the maximum number of pages assigned to a student.

INTUTION:

If one student was assigned all the books, it'd take them how long? That's the upper limit,
and if a student was assigned one book at a time, that book would be at least the one with the
most pages assuming reading rate is the same.

So  we search between max(pages) and sum(pages)

left= the longest book. How long will take all reading the longest book is the lower limit
right= the total number of pages of all books.  i.e. how long a single student would take reading all
of them.*/
public class AllocateBookPages {
    
    public static int allocate(int pages[], int students){

        int left=0;
        int right=0;
        for(int page: pages){
            left=Math.max(left,page);
            right+=page;
        }

        //do a binary search
        int answer=right;
        while(left<=right){
            int mid=left+(right-left)/2;

            if(canAllocate(pages, mid, students)){
                //we need a slower reading speed
                answer=mid;
                right=mid-1;
            }else{
                //a faster reading speed
                left=mid+1;
            }
        }

        return answer;
    }

    private static boolean canAllocate(int[] pages, int maxPagesPerStudent, int students){
        int studentsUsed=1;
        int currentTotalPages=0;

        for(int p: pages){
            if(currentTotalPages+p > maxPagesPerStudent){ // we have surpassed a single student's capscity
                studentsUsed++;
                currentTotalPages=0;
            }

            currentTotalPages+=p;

            if(studentsUsed > students){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[] books = {12, 34, 67, 90};
        int students = 2;
        int result = allocate(books, students);
        System.out.println("Minimum maximum pages = " + result); // Expected: 113
    }
}
