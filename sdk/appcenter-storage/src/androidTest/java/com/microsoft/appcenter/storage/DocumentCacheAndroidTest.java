package com.microsoft.appcenter.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.microsoft.appcenter.storage.models.Document;
import com.microsoft.appcenter.storage.models.ReadOptions;
import com.microsoft.appcenter.storage.models.WriteOptions;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SuppressWarnings("unused")
public class DocumentCacheAndroidTest {

    public static final String TEST_VALUE = "Test value";
    public static final String PARTITION = "partition";
    public static final String ID = "id";
    /**
     * Context instance.
     */
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private DocumentCache mDocumentCache;

    @BeforeClass
    public static void setUpClass() {
        sContext = InstrumentationRegistry.getTargetContext();
    }

    @Before
    public void setUp() {
        mDocumentCache = new DocumentCache(sContext);
    }

    @AfterClass
    public static void tearDownClass() {

        /* Delete database. */
        sContext.deleteDatabase(DocumentCache.DATABASE);
    }

    @Test
    public void writeReadDelete() {
        Document<String> document = new Document<>(TEST_VALUE, PARTITION, ID);
        mDocumentCache.write(document, new WriteOptions());
        Document<String> cachedDocument = mDocumentCache.read(PARTITION, ID, String.class, new ReadOptions());
        assertNotNull(cachedDocument);
        assertEquals(document.getDocument(), cachedDocument.getDocument());
        mDocumentCache.delete(PARTITION, ID);
        Document<String> deletedDocument = mDocumentCache.read(PARTITION, ID, String.class, new ReadOptions());
        assertNotNull(deletedDocument);
        assertNull(deletedDocument.getDocument());
        assertNotNull(deletedDocument.getError());
    }
}
