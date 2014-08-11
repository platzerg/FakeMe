/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.platzerworld.fakefb.twitter4j.savedsearches;

import twitter4j.SavedSearch;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Shows one single saved search.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class ShowSavedSearch {
    /**
     * Usage: java twitter4j.examples.savedsearches.ShowSavedSearch [saved search id]
     *
     * @param args message
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.savedsearches.ShowSavedSearch [saved search id]");
            System.exit(-1);
        }
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            SavedSearch savedSearch = twitter.showSavedSearch(Integer.parseInt(args[0]));
            System.out.println("[name:" + savedSearch.getName() + " query:" + savedSearch.getQuery() + " id:"
                    + savedSearch.getId() + "]");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get the saved search: " + te.getMessage());
            System.exit(-1);
        }
    }
}
