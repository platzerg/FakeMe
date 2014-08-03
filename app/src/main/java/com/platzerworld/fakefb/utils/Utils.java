package com.platzerworld.fakefb.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;

import com.facebook.Response;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.platzerworld.fakefb.utils.entities.Story;
import com.platzerworld.fakefb.utils.entities.User;

public class Utils {
	
	public static final String EMPTY = "";
	public static final String CHARSET_NAME = "UTF-8";
	/**
	 * Take screenshot of the activity including the action bar
	 * 
	 * @param activity
	 * @return The screenshot of the activity including the action bar
	 */
	public static Bitmap takeScreenshot(Activity activity) {
		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decorChild.setDrawingCacheEnabled(true);
		decorChild.buildDrawingCache();
		Bitmap drawingCache = decorChild.getDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(drawingCache);
		decorChild.setDrawingCacheEnabled(false);
		return bitmap;
	}

	/**
	 * Print hash key
	 */
	public static void printHashKey(Context context) {
		try {
			String TAG = "com.sromku.simple.fb.example";
			PackageInfo info = context.getPackageManager().getPackageInfo(TAG, PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.d(TAG, "keyHash: " + keyHash);
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * Update language
	 * 
	 * @param code
	 *            The language code. Like: en, cz, iw, ...
	 */
	public static void updateLanguage(Context context, String code) {
		Locale locale = new Locale(code);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

	/**
	 * Build alert dialog with properties and data
	 * 
	 * @param pairs
	 * @return {@link android.app.AlertDialog}
	 */
	public static AlertDialog buildProfileResultDialog(Activity activity, Pair<String, String>... pairs) {
		StringBuilder stringBuilder = new StringBuilder();

		for (Pair<String, String> pair : pairs) {
			stringBuilder.append(String.format("<h3>%s</h3>", pair.first));
			stringBuilder.append(String.valueOf(pair.second));
			stringBuilder.append("<br><br>");
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(Html.fromHtml(stringBuilder.toString())).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		AlertDialog alertDialog = builder.create();
		return alertDialog;
	}

	public static String toHtml(Object object) {
		StringBuilder stringBuilder = new StringBuilder(256);
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(object);
				stringBuilder.append("<b>");
				stringBuilder.append(field.getName().substring(1, field.getName().length()));
				stringBuilder.append(": ");
				stringBuilder.append("</b>");
				stringBuilder.append(val);
				stringBuilder.append("<br>");
			}
		} catch (Exception e) {
			// Do nothing
		}
		return stringBuilder.toString();
	}

	public static byte[] getSamleVideo(Context context, String assetFile) {
		try {
			InputStream inputStream = context.getAssets().open(assetFile);
			return getBytes(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static byte[] getBytes(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}
	
	
	public String getFacebookSDKVersion() {
		String sdkVersion = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			Class<?> cls = classLoader.loadClass("com.facebook.FacebookSdkVersion");
			Field field = cls.getField("BUILD");
			sdkVersion = String.valueOf(field.get(null));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sdkVersion;
	}

	public static String getHashKey(Context context) {
		// Add code to print out the key hash
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				return Base64.encodeToString(md.digest(), Base64.DEFAULT);
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		return null;
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 * 
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(Iterator<?> iterator, String separator) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return first == null ? EMPTY : first.toString();
		}
		StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(first);
		}
		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 * 
	 * @param <T>
	 * 
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static <T> String join(Iterator<T> iterator, String separator, Process<T> process) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		T first = iterator.next();
		if (!iterator.hasNext()) {
			return first == null ? EMPTY : process.process(first);
		}
		StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(process.process(first));
		}
		while (iterator.hasNext()) {
			buf.append(separator);
			T obj = iterator.next();
			if (obj != null) {
				buf.append(process.process(obj));
			}
		}
		return buf.toString();
	}

	public static String join(Map<?, ?> map, char separator, char valueStartChar, char valueEndChar) {

		if (map == null) {
			return null;
		}
		if (map.size() == 0) {
			return EMPTY;
		}
		StringBuilder buf = new StringBuilder(256);
		boolean isFirst = true;
		for (Entry<?, ?> entry : map.entrySet()) {
			if (isFirst) {
				buf.append(entry.getKey());
				buf.append(valueStartChar);
				buf.append(entry.getValue());
				buf.append(valueEndChar);
				isFirst = false;
			} else {
				buf.append(separator);
				buf.append(entry.getKey());
				buf.append(valueStartChar);
				buf.append(entry.getValue());
				buf.append(valueEndChar);
			}
		}

		return buf.toString();
	}

	public static <T> List<T> convert(JSONArray jsonArray, StringConverter<T> converter) {
		List<T> list = new ArrayList<T>();
		if (jsonArray == null || jsonArray.length() == 0) {
			return list;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(converter.convert(jsonArray.optString(i)));
		}

		return list;
	}

	public static <T extends GraphObject> List<T> typedListFromResponse(Response response, Class<T> clazz) {
		GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
		if (multiResult == null) {
			return null;
		}
		GraphObjectList<GraphObject> data = multiResult.getData();
		if (data == null) {
			return null;
		}
		return data.castToListOf(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Response response, Type type) {
		try {
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Class<?> rawType = (Class<?>) parameterizedType.getRawType();
				if (rawType.getName().equals(List.class.getName())) {
					// if the T is of List type
					List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
					Class<?> actualType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
					Method method = actualType.getMethod("create", GraphObject.class);
					List<Object> list = ArrayList.class.newInstance();
					for (GraphObject graphObject : graphObjects) {
						Object object = method.invoke(null, graphObject);
						list.add(object);
					}
					return (T) list;
				}
			} else {
				Class<?> rawType = (Class<?>) type;
				GraphObject graphObject = response.getGraphObject();
				Method method = rawType.getMethod("create", GraphObject.class);
				Object object = method.invoke(null, graphObject);
				return (T) object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> createList(GraphObject graphObject, String property, Converter<T> converter) {
		List<T> result = new ArrayList<T>();
		if (graphObject == null) {
			return result;
		}

		GraphObjectList<GraphObject> graphObjects = graphObject.getPropertyAsList(property, GraphObject.class);
		if (graphObjects == null || graphObjects.size() == 0) {
			return result;
		}

		ListIterator<GraphObject> iterator = graphObjects.listIterator();
		while (iterator.hasNext()) {
			GraphObject graphObjectItr = iterator.next();
			T t = converter.convert(graphObjectItr);
			result.add(t);
		}
		return result;
	}

	public static <T> List<T> createList(GraphObject graphObject, String property, String rootCollectionJsonProperty, Converter<T> converter) {
		List<T> result = new ArrayList<T>();
		if (graphObject == null) {
			return result;
		}

		GraphObject collectionGraph = getPropertyGraphObject(graphObject, property);
		if (collectionGraph == null) {
			return result;
		}

		GraphObjectList<GraphObject> graphObjects = collectionGraph.getPropertyAsList(rootCollectionJsonProperty, GraphObject.class);
		if (graphObjects == null || graphObjects.size() == 0) {
			return result;
		}

		ListIterator<GraphObject> iterator = graphObjects.listIterator();
		while (iterator.hasNext()) {
			GraphObject graphObjectItr = iterator.next();
			T t = converter.convert(graphObjectItr);
			result.add(t);
		}
		return result;
	}

	public static <T> List<T> createListAggregateValues(GraphObject graphObject, String property, Converter<T> converter) {
		List<T> result = new ArrayList<T>();
		if (graphObject == null) {
			return result;
		}

		GraphObject mapGraph = graphObject.getPropertyAs(property, GraphObject.class);
		if (mapGraph == null) {
			return result;
		}

		// get the map of objects and have them in ordered way
		Map<String, Object> map = mapGraph.asMap();
		Set<String> keySet = map.keySet();
		SortedSet<String> keys = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String lhs, String rhs) {
				return Integer.valueOf(lhs) - Integer.valueOf(rhs);
			}
		});
		keys.addAll(keySet);

		// iterate and create entity
		for (String key : keys) {
			GraphObjectList<GraphObject> graphObjects = mapGraph.getPropertyAsList(key, GraphObject.class);
			if (graphObjects == null || graphObjects.size() == 0) {
				continue;
			}

			ListIterator<GraphObject> iterator = graphObjects.listIterator();
			while (iterator.hasNext()) {
				GraphObject graphObjectItr = iterator.next();
				T t = converter.convert(graphObjectItr);
				result.add(t);
			}
		}

		return result;
	}

	public interface GeneralConverter<T, E> {
		T convert(E e);
	}

	public interface Converter<T> extends GeneralConverter<T, GraphObject> {
	}

	public interface StringConverter<T> extends GeneralConverter<T, String> {
	}

	public interface Process<T> {
		String process(T t);
	}

	public static String getPropertyInsideProperty(GraphObject graphObject, String parent, String child) {
		if (graphObject == null) {
			return null;
		}

		JSONObject jsonObject = (JSONObject) graphObject.getProperty(parent);
		if (jsonObject != null) {
			return String.valueOf(jsonObject.opt(child));
		}
		return null;
	}

	public static String getPropertyString(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		return String.valueOf(graphObject.getProperty(property));
	}

	public static Long getPropertyLong(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		Object value = graphObject.getProperty(property);
		if (value == null || value.equals(EMPTY)) {
			return null;
		}

		try {
			return Long.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Boolean getPropertyBoolean(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		Object value = graphObject.getProperty(property);
		if (value == null || value.equals(EMPTY)) {
			return null;
		}
		return Boolean.valueOf(String.valueOf(value));
	}

	public static Integer getPropertyInteger(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		Object value = graphObject.getProperty(property);
		if (value == null || value.equals(EMPTY)) {
			return null;
		}

		try {
			return Integer.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			return null;
		}

	}

	public static Double getPropertyDouble(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		Object value = graphObject.getProperty(property);
		if (value == null || value.equals(EMPTY)) {
			return null;
		}
		return Double.valueOf(String.valueOf(value));
	}

	public static JSONArray getPropertyJsonArray(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		Object value = graphObject.getProperty(property);
		if (value == null || value.equals(EMPTY)) {
			return null;
		}

		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(value);
			return jsonArray;
		} catch (JSONException e) {
			try {
				return (JSONArray) value;
			} catch (Exception e1) {
			}
		}
		return null;
	}

	public static GraphObject getPropertyGraphObject(GraphObject graphObject, String property) {
		if (graphObject == null) {
			return null;
		}
		return graphObject.getPropertyAs(property, GraphObject.class);
	}

	public static User createUser(GraphObject graphObject, String parent) {
		if (graphObject == null) {
			return null;
		}
		GraphObject userGraphObject = getPropertyGraphObject(graphObject, parent);
		if (userGraphObject == null) {
			return null;
		}
		return createUser(userGraphObject);
	}

	public static User createUser(GraphObject graphObject) {
		final String id = String.valueOf(graphObject.getProperty("id"));
		final String name = String.valueOf(graphObject.getProperty("name"));

		User user = new User() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getId() {
				return id;
			}
		};

		return user;
	}

	public static String encodeUrl(Bundle parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : parameters.keySet()) {
			Object parameter = parameters.get(key);
			if (!(parameter instanceof String)) {
				continue;
			}

			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			try {
				sb.append(URLEncoder.encode(key, CHARSET_NAME)).append("=").append(URLEncoder.encode(parameters.getString(key), CHARSET_NAME));
			} catch (UnsupportedEncodingException e) {
				Logger.logError(Story.class, "Error enconding URL", e);
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("resource")
	public static String encode(String key, String data) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			mac.init(secretKey);
			byte[] bytes = mac.doFinal(data.getBytes());
			StringBuilder sb = new StringBuilder(bytes.length * 2);
			Formatter formatter = new Formatter(sb);
			for (byte b : bytes) {
				formatter.format("%02x", b);
			}
			return sb.toString();
		} catch (Exception e) {
			Logger.logError(Utils.class, "Failed to create sha256", e);
			return null;
		}
	}

}
