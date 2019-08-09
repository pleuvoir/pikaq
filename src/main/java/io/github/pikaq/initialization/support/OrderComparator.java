package io.github.pikaq.initialization.support;

import java.util.Comparator;

public class OrderComparator implements Comparator<Ordered> {

	@Override
	public int compare(Ordered o1, Ordered o2) {
		int i1 = (o1 instanceof Ordered ? ((Ordered) o1).getOrder() : Integer.MAX_VALUE);
		int i2 = (o2 instanceof Ordered ? ((Ordered) o2).getOrder() : Integer.MAX_VALUE);

		if (i1 < i2)
			return -1;
		else if (i1 > i2)
			return 1;
		else
			return 0;
	}

}
