package net.pool.station.core.bootstrap.rest.response;

import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.springframework.data.domain.Sort;

public record MySorter(Sort sort) {
    public static Sort of(String sorter) {

        if (MyObjectUtils.isNotEmpty(sorter)) {
            String[] sortSplit = sorter.split("_");
            if (MyObjectUtils.isEquals(sortSplit.length, 2)) {
                if (sortSplit[1].equals("asc")) {
                    return Sort.by(sortSplit[0]).ascending();
                }

                if (sortSplit[1].equals("desc")) {
                    return Sort.by(sortSplit[0]).descending();
                }
            }

            if (MyObjectUtils.isEquals(sortSplit.length, 1)) {
                return Sort.by(sortSplit[0]).descending();
            }
        }

        return Sort.unsorted();
    }
}
