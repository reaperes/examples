package com.reaperes;

import org.junit.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UriAppend {
	private static final String baseUrl = "http://www.test.com";
	private static final String directory = "test/image";
	private static final String directory2 = "tree/sample";
	private static final String file = "img.png";

	private static final String expect = "http://www.test.com/test/image/tree/sample/img.png";

	public static void main(String[] args) throws URISyntaxException {
		uriPathCombination();   // success
		uriResolve();   // fail
	}

	private static void uriPathCombination() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
		Path path = Paths.get(directory, directory2, file);

		builder.path(path.toString());
		String uri = builder.build().toString();

		Assert.assertEquals(expect, uri);
	}

	private static void uriResolve() throws URISyntaxException {
		URI uri = new URI(baseUrl);
		URI uri2 = new URI(directory);
		URI uri3 = new URI(directory2);
		URI uri4 = new URI(file);

		URI newUri = uri.resolve(uri2);
		newUri = newUri.resolve(uri3);
		newUri = newUri.resolve(uri4);
		Assert.assertNotEquals(expect, newUri.toString());
	}
}
