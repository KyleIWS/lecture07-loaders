package edu.uw.loaderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by iguest on 4/18/17.
 */

public class VolleyRequestSingleton {
    equestQueue mRequestQueue;

    // Instantiate the cache
    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

    // Set up the network to use HttpURLConnection as the HTTP client.
    Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
    mRequestQueue = new RequestQueue(cache, network);

// Start the queue
mRequestQueue.start();

    String url ="http://www.example.com";

    // Formulate the request and handle the response.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Do something with the response
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });

// Add the request to the RequestQueue.
mRequestQueue.add(stringRequest);



    public class VolleyRequestSingleton {
        private static edu.uw.loaderdemo.VolleyRequestSingleton mInstance;
        private RequestQueue mRequestQueue;
        private ImageLoader mImageLoader;
        private static Context mCtx;

        private VolleyRequestSingleton(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();

            mImageLoader = new ImageLoader(mRequestQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }

        public static synchronized VolleyRequestSingleton getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new edu.uw.loaderdemo.VolleyRequestSingleton(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }

        public ImageLoader getImageLoader() {
            new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> cache = new LruCache(String, Bitmap)(20);

                public LruCache<String> getBitmap() {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    catch.put(url, bitmap);
                }
            }

            return mImageLoader;
        }
    }







}
