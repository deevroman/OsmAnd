package net.osmand.plus.download.local.dialogs;

import androidx.annotation.NonNull;

import net.osmand.Collator;
import net.osmand.OsmAndCollator;
import net.osmand.plus.download.local.LocalItem;
import net.osmand.plus.settings.enums.MapsSortMode;

import java.util.Comparator;

public class MapsComparator implements Comparator<LocalItem> {

	public final MapsSortMode sortMode;
	public final Collator collator = OsmAndCollator.primaryCollator();

	public MapsComparator(@NonNull MapsSortMode sortMode) {
		this.sortMode = sortMode;
	}

	@Override
	public int compare(LocalItem item1, LocalItem item2) {
		switch (sortMode) {
			case NAME_ASCENDING:
				return compareItemNames(item1, item2);
			case NAME_DESCENDING:
				return -compareItemNames(item1, item2);
			case DATE_ASCENDING:
				return -Long.compare(item1.getLastModified(), item2.getLastModified());
			case DATE_DESCENDING:
				return Long.compare(item1.getLastModified(), item2.getLastModified());
			case SIZE_DESCENDING:
				return -Long.compare(item1.getSize(), item2.getSize());
			case SIZE_ASCENDING:
				return Long.compare(item1.getSize(), item2.getSize());
		}
		return 0;
	}

	private int compareItemNames(@NonNull LocalItem item1, @NonNull LocalItem item2) {
		return compareNames(item1.getName().toString(), item2.getName().toString());
	}

	private int compareNames(@NonNull String name1, @NonNull String name2) {
		return collator.compare(name1, name2);
	}
}
