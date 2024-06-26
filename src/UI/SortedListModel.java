package UI;

import javax.swing.*;
import java.util.*;

class SortedListModel extends AbstractListModel
{
    SortedSet<Object> model;
    
    public SortedListModel() {
        this.model = new TreeSet<Object>();
    }
    
    @Override
    public int getSize() {
        return this.model.size();
    }
    
    @Override
    public Object getElementAt(final int index) {
        return this.model.toArray()[index];
    }
    
    public void add(final Object element) {
        if (this.model.add(element)) {
            this.fireContentsChanged(this, 0, this.getSize());
        }
    }
    
    public void addAll(final Object[] elements) {
        final Collection<Object> c = Arrays.asList(elements);
        this.model.addAll(c);
        this.fireContentsChanged(this, 0, this.getSize());
    }
    
    public void clear() {
        this.model.clear();
        this.fireContentsChanged(this, 0, this.getSize());
    }
    
    public boolean contains(final Object element) {
        return this.model.contains(element);
    }
    
    public Object firstElement() {
        return this.model.first();
    }
    
    public Iterator iterator() {
        return this.model.iterator();
    }
    
    public Object lastElement() {
        return this.model.last();
    }
    
    public boolean removeElement(final Object element) {
        final boolean removed = this.model.remove(element);
        if (removed) {
            this.fireContentsChanged(this, 0, this.getSize());
        }
        return removed;
    }
}
