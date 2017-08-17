package unsafe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ObjectInfo {
    /**
     * Field name
     */
    public final String name;
    /**
     * Field type name
     */
    public final String type;
    /**
     * Field data formatted as string
     */
    private final String contents;
    /**
     * Field offset from the start of parent object
     */
    public final int offset;
    /**
     * Memory occupied by this field
     */
    public final int length;
    /**
     * Offset of the first cell in the array
     */
    private final int arrayBase;
    /**
     * Size of a cell in the array
     */
    private final int arrayElementSize;
    /**
     * Memory occupied by underlying array (shallow), if this is array type
     */
    private final int arraySize;
    /**
     * This object fields
     */
    private final List<ObjectInfo> children;

    ObjectInfo(String name, String type, String contents, int offset, int length, int arraySize,
               int arrayBase, int arrayElementSize) {
        this.name = name;
        this.type = type;
        this.contents = contents;
        this.offset = offset;
        this.length = length;
        this.arraySize = arraySize;
        this.arrayBase = arrayBase;
        this.arrayElementSize = arrayElementSize;
        children = new ArrayList<>(1);
    }

    void addChild(final ObjectInfo info) {
        if (info != null)
            children.add(info);
    }

    /**
     * Get the full amount of memory occupied by a given object. This value may be slightly less than
     * an actual value because we don't worry about memory alignment - possible padding after the last object field.
     * <p>
     * The result is equal to the last field offset + last field length + all array sizes + all child objects deep sizes
     *
     * @return Deep object size
     */
    long getDeepSize() {
        return length + arraySize + getUnderlyingSize(arraySize != 0);
    }

    private long getUnderlyingSize(final boolean isArray) {
        long size = 0;
        for (final ObjectInfo child : children)
            size += child.arraySize + child.getUnderlyingSize(child.arraySize != 0);
        if (!isArray && !children.isEmpty())
            size += children.get(children.size() - 1).offset + children.get(children.size() - 1).length;
        return size;
    }

    private static final class OffsetComparator implements Comparator<ObjectInfo> {
        public int compare(final ObjectInfo o1, final ObjectInfo o2) {
            return o1.offset - o2.offset; //safe because offsets are small non-negative numbers    
        }
    }

    //sort all children by their offset    
    public void sort() {
        children.sort(new OffsetComparator());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        toStringHelper(sb, 0);
        return sb.toString();
    }

    private void toStringHelper(final StringBuilder sb, final int depth) {
        depth(sb, depth).append("name=").append(name).append(", type=").append(type)
                .append(", contents=").append(contents).append(", offset=").append(offset)
                .append(", length=").append(length);
        if (arraySize > 0) {
            sb.append(", arrayBase=").append(arrayBase);
            sb.append(", arrayElemSize=").append(arrayElementSize);
            sb.append(", arraySize=").append(arraySize);
        }
        for (final ObjectInfo child : children) {
            sb.append('n');
            child.toStringHelper(sb, depth + 1);
        }
    }

    private StringBuilder depth(final StringBuilder sb, final int depth) {
        for (int i = 0; i < depth; ++i)
            sb.append('t');
        return sb;
    }
}
