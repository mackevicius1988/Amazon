package com.mm.amazon.inventory.services.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import com.mm.amazon.inventory.domain.Entry;
import com.mm.amazon.inventory.services.ImageService;

/**
 * 
 * @author mmackevicius
 *
 */
@Service
public class ImageServiceImpl implements ImageService {

	// private static final String IMGUR_API_KEY =
	// "e7414d64af725162e119222b2fa3cfc319f60c98";
	// private final String URI = "https://api.imgur.com/3/upload.json";
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	private UsernamePasswordCredentialsProvider cridentials = new UsernamePasswordCredentialsProvider("mackevicius1988",
			"Kestutis1");

	private String remoteRepoUrl = "https://github.com/mackevicius1988/images.git";

	public String uploadImage(String image) {
		try {

			// Credential credential = new Credential.Builder(new
			// QueryParameterAccessMethod())
			// .setTransport(httpTransport)
			// .setJsonFactory(jsonFactory)
			// .setServiceAccountId("mackevicius1988@gmail.com")
			// .setServiceAccountPrivateKeyFromP12File(new
			// File("C:\\TEST-7ec0bd71356f.p12"))
			// .build();
			// myService.setOAuth2Credentials(credential);
			// URL baseSearchUrl = new
			// URL("https://picasaweb.google.com/data/feed/api/all");
			//
			// Query myQuery = new Query(baseSearchUrl);
			//
			// AlbumFeed searchResultsFeed = myService.query(myQuery,
			// AlbumFeed.class);
			//
			// for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
			// System.out.println(photo.getTitle().getPlainText());
			// System.out.println(photo.getAlbumId());
			// }
			return "";
			//
			// URL albumPostUrl = new
			// URL("https://picasaweb.google.com/data/feed/api/user/mackevicius1988/albumid/20140612");
			//
			// PhotoEntry myPhoto = new PhotoEntry();
			// myPhoto.setTitle(new PlainTextConstruct("Puppies FTW"));
			// myPhoto.setDescription(new PlainTextConstruct("Puppies are the
			// greatest."));
			// myPhoto.setClient("myClientName");
			//
			// MediaFileSource myMedia = new MediaFileSource(new
			// File("/home/liz/puppies.jpg"), "image/jpeg");
			// myPhoto.setMediaSource(myMedia);
			//
			// PhotoEntry returnedPhoto = myService.insert(albumPostUrl,
			// myPhoto);

			// HttpClient httpClient = new DefaultHttpClient();
			// HttpContext localContext = new BasicHttpContext();
			// HttpPost httpPost = new HttpPost(URL);
			//
			// final MultipartEntityBuilder entity =
			// MultipartEntityBuilder.create();
			//
			// httpPost.setHeader("Authorization", "Client-ID " +
			// "0dc00b9ecb8559f");
			// entity.addPart("image", new StringBody(image,
			// ContentType.APPLICATION_JSON));
			// entity.addPart("key", new StringBody(IMGUR_API_KEY,
			// ContentType.APPLICATION_JSON));
			//
			// httpPost.setEntity(entity.build());
			//
			// final HttpResponse response = httpClient.execute(httpPost,
			// localContext);
			//
			// for (Header header : response.getAllHeaders()) {
			// HeaderElement[] elements = header.getElements();
			// System.out.println(header.getName() + " : " + header.getValue() +
			// ": " + elements);
			//
			// }
			//
			// final String responseString =
			// EntityUtils.toString(response.getEntity());
			// System.out.println(responseString);
			//
			// JSONParser parser = new JSONParser();
			// JSONObject json = (JSONObject) parser.parse(responseString);
			//
			// JSONObject data = (JSONObject) json.get("data");
			// String id = data.get("id") != null ? data.get("id").toString() :
			// "";
			//
			// if (data.get("error") != null) {
			// throw new Exception(data.get("error").toString());
			// }
			// return "https://i.imgur.com/" + id + ".jpg";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Entry> uploadImages(String imageDirectoryUrl, BigDecimal price) {
		List<String> urls = new ArrayList<>();
		String destinationFolder = "images" + dateFormatter.format(new Date());
		System.out.println("Images processing starting...");
		String localRepoUrl = "C:\\repository\\repo-" + dateFormatter.format(new Date());
		File repoDirectory = new File(localRepoUrl);
		try (Git git = Git.cloneRepository().setURI(remoteRepoUrl).setCredentialsProvider(cridentials)
				.setDirectory(repoDirectory).call();) {

			System.out.println("Git created");

			copyFilesToLocalRepo(imageDirectoryUrl, localRepoUrl, destinationFolder);

			System.out.println("Files copied");

			AddCommand add = git.add();
			CommitCommand commit = git.commit();
			PushCommand push = git.push();
			add.addFilepattern(".");
			DirCache addResult = add.call();

			System.out.println("Files added");
			commit.setMessage("Uploaded: " + dateFormatter.format(new Date()));
			RevCommit commitResult = commit.call();

			RevTree tree = commitResult.getTree();

			System.out.println("Files commited");
			push.setRemote(remoteRepoUrl);
			push.setCredentialsProvider(cridentials);
			Iterable<PushResult> list = push.call();

			System.out.println("Files pushed: " + list);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return getObjects(imageDirectoryUrl, price, destinationFolder);
	}

	private List<Entry> getObjects(String imageDirectoryUrl, BigDecimal price, String destinationFolder) {
		List<Entry> ret = new ArrayList<>();
		Path dir = Paths.get(imageDirectoryUrl);
		System.out.println(dir.toString());
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				Entry e = new Entry();
				e.setImageUrl("https://raw.githubusercontent.com/mackevicius1988/images/master/" + destinationFolder
						+ "/" + entry.getFileName() + "?raw=true");
				e.setPrice(price);
				e.setTitle(entry.getFileName().toString());
				ret.add(e);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return ret;
	}

	private void copyFilesToLocalRepo(String imageDirectoryUrl, String localRepoUrl, String destinationFolder)
			throws IOException {
		FileUtils.copyDirectory(new File(imageDirectoryUrl), new File(localRepoUrl + "\\" + destinationFolder));
	}
}
