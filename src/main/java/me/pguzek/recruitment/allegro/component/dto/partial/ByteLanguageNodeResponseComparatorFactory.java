package me.pguzek.recruitment.allegro.component.dto.partial;

import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.component.dto.response.ByteLanguageNodeResponseDto;
import org.springframework.lang.NonNull;

import java.util.Comparator;

public class ByteLanguageNodeResponseComparatorFactory {

    public enum Direction {
        ASC, DESC
    }

    public enum OrderBy {
        BYTES, LANGUAGE
    }

    @NonNull
    public static Comparator<ByteLanguageNodeResponseDto> build(@NonNull Direction direction, @NonNull OrderBy orderBy) {
        return switch (orderBy) {
            case BYTES -> new BytesComparator(direction);
            case LANGUAGE -> new LanguageComparator(direction);
        };
    }

    @RequiredArgsConstructor
    protected static class BytesComparator implements Comparator<ByteLanguageNodeResponseDto> {
        private final Direction type;

        @Override
        public int compare(@NonNull ByteLanguageNodeResponseDto o1, @NonNull ByteLanguageNodeResponseDto o2) {
            var result =  Long.compare(o1.getTotalBytes(), o2.getTotalBytes());
            return Direction.ASC.equals(type) ? result : -result;
        }
    }

    @RequiredArgsConstructor
    protected static class LanguageComparator implements Comparator<ByteLanguageNodeResponseDto> {
        private final Direction type;

        @Override
        public int compare(@NonNull ByteLanguageNodeResponseDto o1, @NonNull ByteLanguageNodeResponseDto o2) {
            var result =  o1.getLanguageName().compareTo(o2.getLanguageName());
            return Direction.ASC.equals(type) ? result : -result;
        }
    }

}
