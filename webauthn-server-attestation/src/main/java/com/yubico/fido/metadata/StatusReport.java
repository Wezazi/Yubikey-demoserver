package com.yubico.fido.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yubico.internal.util.CollectionUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Contains an {@link AuthenticatorStatus} and additional data associated with it, if any.
 *
 * @see <a
 *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
 *     Metadata Service §3.1.3. StatusReport dictionary</a>
 */
@Value
@Builder
@Jacksonized
public class StatusReport {

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  @NonNull AuthenticatorStatus status;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  LocalDate effectiveDate;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  Long authenticatorVersion;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  @JsonDeserialize(converter = CertFromBase64Converter.class)
  @JsonSerialize(converter = CertToBase64Converter.class)
  X509Certificate certificate;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  @JsonProperty("url")
  @Getter(AccessLevel.NONE)
  String url;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  String certificationDescriptor;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  String certificateNumber;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  String certificationPolicyVersion;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  List<String> certificationProfiles;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  String certificationRequirementsVersion;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  String sunsetDate;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  Long fipsRevision;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  Long fipsPhysicalSecurityLevel;

  private StatusReport(
      @NonNull AuthenticatorStatus status,
      LocalDate effectiveDate,
      Long authenticatorVersion,
      X509Certificate certificate,
      String url,
      String certificationDescriptor,
      String certificateNumber,
      String certificationPolicyVersion,
      List<String> certificationProfiles,
      String certificationRequirementsVersion,
      String sunsetDate,
      Long fipsRevision,
      Long fipsPhysicalSecurityLevel) {
    this.status = status;
    this.effectiveDate = effectiveDate;
    this.authenticatorVersion = authenticatorVersion;
    this.certificate = certificate;
    this.url = url;
    this.certificationDescriptor = certificationDescriptor;
    this.certificateNumber = certificateNumber;
    this.certificationPolicyVersion = certificationPolicyVersion;
    this.certificationProfiles = CollectionUtil.immutableListOrEmpty(certificationProfiles);
    this.certificationRequirementsVersion = certificationRequirementsVersion;
    this.sunsetDate = sunsetDate;
    this.fipsRevision = fipsRevision;
    this.fipsPhysicalSecurityLevel = fipsPhysicalSecurityLevel;
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<LocalDate> getEffectiveDate() {
    return Optional.ofNullable(effectiveDate);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<Long> getAuthenticatorVersion() {
    return Optional.ofNullable(authenticatorVersion);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  @JsonIgnore
  public Optional<X509Certificate> getCertificate() {
    return Optional.ofNullable(this.certificate);
  }

  /**
   * Attempt to parse the {@link #getUrlAsString() url} property, if any, as a {@link URL}.
   *
   * @return A present value if and only if {@link #getUrlAsString()} is present and a valid URL.
   */
  public Optional<URL> getUrl() {
    try {
      return Optional.of(new URL(url));
    } catch (MalformedURLException e) {
      return Optional.empty();
    }
  }

  /**
   * Get the raw <code>url</code> property of this {@link StatusReport} object. This may or may not
   * be a valid URL.
   *
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  @JsonIgnore
  public Optional<String> getUrlAsString() {
    return Optional.ofNullable(this.url);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<String> getCertificationDescriptor() {
    return Optional.ofNullable(this.certificationDescriptor);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<String> getCertificateNumber() {
    return Optional.ofNullable(this.certificateNumber);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<String> getCertificationPolicyVersion() {
    return Optional.ofNullable(this.certificationPolicyVersion);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.0-ps-20210518.html#statusreport-dictionary">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<String> getCertificationRequirementsVersion() {
    return Optional.ofNullable(this.certificationRequirementsVersion);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<String> getSunsetDate() {
    return Optional.ofNullable(this.sunsetDate);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<Long> getFipsRevision() {
    return Optional.ofNullable(fipsRevision);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/mds/fido-metadata-service-v3.1-ps-20250521.html#sctn-stat-rep">FIDO
   *     Metadata Service §3.1.3. StatusReport dictionary</a>
   */
  public Optional<Long> getFipsPhysicalSecurityLevel() {
    return Optional.ofNullable(fipsPhysicalSecurityLevel);
  }
}
