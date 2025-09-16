package net.pool.station.core.domain.media;

import lombok.Builder;
import net.pool.station.core.bootstrap.enums.EMediaType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.apache.tika.Tika;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

@Builder
public record Media(
        String url,
        String mediaTypeCode,
        String mediaTypeName
) {
    public Media {
        EMediaType mediaType = getMediaType(url);
        mediaTypeCode = mediaType.getCode();
        mediaTypeName = mediaType.getName();
    }

    private EMediaType getMediaType(String url) {
        try (InputStream is = new URI(url).toURL().openStream()) {
            Tika tika = new Tika();
            String mimeType = tika.detect(is);
            return Stream.of(EMediaType.values())
                    .filter(m -> MyObjectUtils.isEquals(mimeType, m.getCode()))
                    .findAny()
                    .orElse(EMediaType.UNDEFINED);
        } catch (Exception e) {
            return EMediaType.UNDEFINED;
        }
    }

    public static List<Media> of(List<String> urls) {
        Tika tika = new Tika();
        return urls.stream()
                .map(url -> {
                    try (InputStream is = new URI(url).toURL().openStream()) {
                        String mimeType = tika.detect(is);
                        return Stream.of(EMediaType.values())
                                .map(m -> Media.builder()
                                        .url(url)
                                        .mediaTypeCode(m.getCode())
                                        .mediaTypeName(m.getName())
                                        .build())
                                .filter(m -> MyObjectUtils.isEquals(mimeType, m.mediaTypeCode))
                                .findAny()
                                .orElse(Media.builder()
                                        .url(url)
                                        .mediaTypeCode(EMediaType.UNDEFINED.getCode())
                                        .mediaTypeName(EMediaType.UNDEFINED.getName())
                                        .build());
                    } catch (Exception e) {
                        return Media.builder()
                                .url(url)
                                .mediaTypeCode(EMediaType.UNDEFINED.getCode())
                                .mediaTypeName(EMediaType.UNDEFINED.getName())
                                .build();
                    }
                })
                .toList();
    }
}
