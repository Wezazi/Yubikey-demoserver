package com.yubico.fido.metadata

import com.yubico.webauthn.data.ByteArray

import java.nio.file.Path
import scala.jdk.OptionConverters.RichOptional

object TestCaches {

  // Cache downloaded items to avoid unnecessary load on remote servers, and so tests don't have to wait for rate limiting

  private val trustRootCacheFile = Path
    .of(sys.env.getOrElse("FIDO_MDS_CACHE_DIR", "."), "trust-root-cache.bin")
    .toFile
  private val blobCacheFile = Path
    .of(sys.env.getOrElse("FIDO_MDS_CACHE_DIR", "."), "blob-cache.bin")
    .toFile

  def trustRootCache: Option[ByteArray] =
    cachedDefaultSettingsDownloader
      .build()
      .readCacheFile(trustRootCacheFile)
      .toScala
  def blobCache: Option[ByteArray] =
    cachedDefaultSettingsDownloader.build().readCacheFile(blobCacheFile).toScala

  def cachedDefaultSettingsDownloader
      : FidoMetadataDownloader.FidoMetadataDownloaderBuilder =
    FidoMetadataDownloader
      .builder()
      .expectLegalHeader(
        "Retrieval and use of this BLOB indicates acceptance of the appropriate agreement located at https://fidoalliance.org/metadata/metadata-legal-terms/"
      )
      .useDefaultTrustRoot()
      .useTrustRootCacheFile(trustRootCacheFile)
      .useDefaultBlob()
      .useBlobCacheFile(blobCacheFile)

  /** Evaluate <code>expr</code> with an exclusive lock on the test cache. */
  def cacheSynchronized[A](expr: => A): A = {
    this.synchronized(expr)
  }

}
