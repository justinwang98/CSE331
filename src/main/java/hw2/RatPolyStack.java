package hw2;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
public final class RatPolyStack implements Iterable<RatPoly> {

  /** Stack containing the RatPoly objects */
  private final Stack<RatPoly> polys;

  // Abstraction Function:
  // Each element of a RatPolyStack, s, is mapped to the
  // corresponding element of polys.
  //
  // RepInvariant:
  // polys != null &&
  // forall i such that (0 <= i < polys.size(), polys.get(i) != null

  /** @spec.effects Constructs a new RatPolyStack, []. */
  @SuppressWarnings("JdkObsolete")
  public RatPolyStack() {
    polys = new Stack<RatPoly>();
    checkRep();
  }

  /**
   * Returns the number of RayPolys in this RatPolyStack.
   *
   * @return the size of this sequence.
   */
  public int size() {
    int size = 0;
    Iterator<RatPoly> it = iterator();
    while (it.hasNext()) {
      size++;
      it.next();
    }
    return size;
  }

  /**
   * Pushes a RatPoly onto the top of this.
   *
   * @param p The RatPoly to push onto this stack.
   * @spec.requires p != null
   * @spec.modifies this
   * @spec.effects this_post = [p]:this
   */
  public void push(RatPoly p) {
    checkRep();
    polys.push(p);
    checkRep();
  }

  /**
   * Removes and returns the top RatPoly.
   *
   * @spec.requires {@code this.size() > 0}
   * @spec.modifies this
   * @spec.effects If this = [p]:S then this_post = S
   * @return p where this = [p]:S
   */
  public RatPoly pop() {
    checkRep();
    RatPoly temp = polys.pop();
    checkRep();
    return temp;
  }

  /**
   * Duplicates the top RatPoly on this.
   *
   * @spec.requires {@code this.size() > 0}
   * @spec.modifies this
   * @spec.effects If this = [p]:S then this_post = [p, p]:S
   */
  public void dup() {
    checkRep();
    polys.push(polys.peek());
    checkRep();
  }

  /**
   * Swaps the top two elements of this.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S
   */
  public void swap() {
    checkRep();
    RatPoly poly1 = polys.pop();
    RatPoly poly2 = polys.pop();
    polys.push(poly1);
    polys.push(poly2);
    checkRep();
  }

  /**
   * Clears the stack.
   *
   * @spec.modifies this
   * @spec.effects this_post = []
   */
  public void clear() {
    checkRep();
    polys.clear();
    checkRep();
  }

  /**
   * Returns the RatPoly that is 'index' elements from the top of the stack.
   *
   * @param index The index of the RatPoly to be retrieved.
   * @spec.requires {@code index >= 0 && index < this.size()}
   * @return If this = S:[p]:T where S.size() = index, then returns p.
   */
  public RatPoly getNthFromTop(int index) {
    checkRep();
    RatPolyStack temp = new RatPolyStack();
    for(int i = 0; i < index; i++) {
      temp.push(polys.pop());
    }
    RatPoly temp2 = polys.peek();
    for (int i = 0; i < index; i++) {
      polys.push(temp.pop());
    }
    checkRep();
    return temp2;
  }

  /**
   * Pops two elements off of the stack, adds them, and places the result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
   */
  public void add() {
    checkRep();
    RatPoly temp = polys.pop();
    RatPoly temp2 = polys.pop();
    polys.push(temp.add(temp2));
    checkRep();
  }

  /**
   * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
   * result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
   */
  public void sub() {
    checkRep();
    RatPoly temp = polys.pop();
    RatPoly temp2 = polys.pop();
    polys.push(temp2.sub(temp));
    checkRep();
  }

  /**
   * Pops two elements off of the stack, multiplies them, and places the result on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
   */
  public void mul() {
    checkRep();
    RatPoly temp = polys.pop();
    RatPoly temp2 = polys.pop();
    polys.push(temp2.mul(temp));
    checkRep();
  }

  /**
   * Divides the next from top poly by the top poly, pops both off the stack, and places the result
   * on top of the stack.
   *
   * @spec.requires {@code this.size() >= 2}
   * @spec.modifies this
   * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
   */
  public void div() {
    checkRep();
    RatPoly temp = polys.pop();
    RatPoly temp2 = polys.pop();
    polys.push(temp2.div(temp));
    checkRep();
  }

  /**
   * Pops the top element off of the stack, differentiates it, and places the result on top of the
   * stack.
   *
   * @spec.requires {@code this.size() >= 1}
   * @spec.modifies this
   * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = derivative of p1
   */
  public void differentiate() {
    checkRep();
    RatPoly temp = polys.pop();
    polys.push(temp.differentiate());
    checkRep();
  }

  /**
   * Pops the top element off of the stack, integrates it, and places the result on top of the
   * stack.
   *
   * @spec.requires {@code this.size() >= 1}
   * @spec.modifies this
   * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = indefinite integral of p1
   *     with integration constant 0
   */
  public void integrate() {
    checkRep();
    RatPoly temp = polys.pop();
    polys.push(temp.antiDifferentiate(new RatNum(0)));
    checkRep();
  }

  /**
   * Returns an iterator of the elements contained in the stack.
   *
   * @return an iterator of the elements contained in the stack in order from the bottom of the
   *     stack to the top of the stack.
   */
  @Override
  public Iterator<RatPoly> iterator() {
    return polys.iterator();
  }

  /** Checks that the representation invariant holds (if any). */
  private void checkRep() {
    assert (polys != null) : "polys should never be null.";

    for (RatPoly p : polys) {
      assert (p != null) : "polys should never contain a null element.";
    }
  }
}
