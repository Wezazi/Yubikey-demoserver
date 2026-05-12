package com.yubico.fido.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yubico.webauthn.data.AuthenticatorTransport;
import com.yubico.webauthn.data.PublicKeyCredentialParameters;
import com.yubico.webauthn.extension.uvm.UserVerificationMethod;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * This dictionary describes supported versions, extensions, AAGUID of the device and its
 * capabilities.
 *
 * <p>See: <a
 * href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
 * to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
 *
 * @see <a
 *     href="https://fidoalliance.org/specs/mds/fido-metadata-statement-v3.0-ps-20210518.html#authenticatorgetinfo-dictionary">FIDO
 *     Metadata Statement §3.12. AuthenticatorGetInfo dictionary</a>
 * @see <a
 *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
 *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
 */
@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties({
  "maxAuthenticatorConfigLength", // Present in example but not defined
  "defaultCredProtect", // Present in example but not defined
  "encIdentifier", // Nonsensical in MDS context
  "encCredStoreState" // Nonsensical in MDS context
})
public class AuthenticatorGetInfo {

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  @NonNull Set<CtapVersion> versions;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Set<String> extensions;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  AAGUID aaguid;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  SupportedCtapOptions options;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxMsgSize;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Set<CtapPinUvAuthProtocolVersion> pinUvAuthProtocols;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxCredentialCountInList;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxCredentialIdLength;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Set<AuthenticatorTransport> transports;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  @JsonDeserialize(using = ListPublicKeyCredentialParametersIgnoringUnknownValuesDeserializer.class)
  List<PublicKeyCredentialParameters> algorithms;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxSerializedLargeBlobArray;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Boolean forcePINChange;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer minPINLength;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer firmwareVersion;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxCredBlobLength;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxRPIDsForSetMinPINLength;

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer preferredPlatformUvAttempts;

  @JsonDeserialize(using = SetFromIntJsonDeserializer.class)
  @JsonSerialize(contentUsing = IntFromSetJsonSerializer.class)
  Set<UserVerificationMethod> uvModality;

  Map<CtapCertificationId, Integer> certifications;
  Integer remainingDiscoverableCredentials;
  Set<Integer> vendorPrototypeConfigCommands;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  List<String> attestationFormats;

  /**
   * <code>true</code> if the <code>longTouchForReset</code> member is set to <code>true</code> or
   * <code>false</code> in the metadata statement. <code>false</code> if the <code>longTouchForReset
   * </code> member is absent in the metadata statement.
   *
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  boolean longTouchForReset;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer uvCountSinceLastPinEntry;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Set<String> transportsForReset;

  /**
   * <code>true</code> if the <code>pinComplexityPolicy</code> member is set to <code>true</code> or
   * <code>false</code> in the metadata statement. <code>false</code> if the <code>
   * pinComplexityPolicy</code> member is absent in the metadata statement.
   *
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  boolean pinComplexityPolicy;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  String pinComplexityPolicyURL;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Integer maxPINLength;

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  Set<Integer> authenticatorConfigCommands;

  AuthenticatorGetInfo(
      @NonNull Set<CtapVersion> versions,
      Set<String> extensions,
      AAGUID aaguid,
      SupportedCtapOptions options,
      Integer maxMsgSize,
      Set<CtapPinUvAuthProtocolVersion> pinUvAuthProtocols,
      Integer maxCredentialCountInList,
      Integer maxCredentialIdLength,
      Set<AuthenticatorTransport> transports,
      List<PublicKeyCredentialParameters> algorithms,
      Integer maxSerializedLargeBlobArray,
      Boolean forcePINChange,
      Integer minPINLength,
      Integer firmwareVersion,
      Integer maxCredBlobLength,
      Integer maxRPIDsForSetMinPINLength,
      Integer preferredPlatformUvAttempts,
      Set<UserVerificationMethod> uvModality,
      Map<CtapCertificationId, Integer> certifications,
      Integer remainingDiscoverableCredentials,
      Set<Integer> vendorPrototypeConfigCommands,
      List<String> attestationFormats,
      Boolean longTouchForReset,
      Integer uvCountSinceLastPinEntry,
      Set<String> transportsForReset,
      Boolean pinComplexityPolicy,
      String pinComplexityPolicyURL,
      Integer maxPINLength,
      Set<Integer> authenticatorConfigCommands) {
    this.versions = versions;
    this.extensions = extensions;
    this.aaguid = aaguid;
    this.options = options;
    this.maxMsgSize = maxMsgSize;
    this.pinUvAuthProtocols = pinUvAuthProtocols;
    this.maxCredentialCountInList = maxCredentialCountInList;
    this.maxCredentialIdLength = maxCredentialIdLength;
    this.transports = transports;
    this.algorithms = algorithms;
    this.maxSerializedLargeBlobArray = maxSerializedLargeBlobArray;
    this.forcePINChange = forcePINChange;
    this.minPINLength = minPINLength;
    this.firmwareVersion = firmwareVersion;
    this.maxCredBlobLength = maxCredBlobLength;
    this.maxRPIDsForSetMinPINLength = maxRPIDsForSetMinPINLength;
    this.preferredPlatformUvAttempts = preferredPlatformUvAttempts;
    this.uvModality = uvModality;
    this.certifications = certifications;
    this.remainingDiscoverableCredentials = remainingDiscoverableCredentials;
    this.vendorPrototypeConfigCommands = vendorPrototypeConfigCommands;
    this.attestationFormats = attestationFormats;
    this.longTouchForReset = longTouchForReset != null;
    this.uvCountSinceLastPinEntry = uvCountSinceLastPinEntry;
    this.transportsForReset = transportsForReset;
    this.pinComplexityPolicy = pinComplexityPolicy != null;
    this.pinComplexityPolicyURL = pinComplexityPolicyURL;
    this.maxPINLength = maxPINLength;
    this.authenticatorConfigCommands = authenticatorConfigCommands;
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<String>> getExtensions() {
    return Optional.ofNullable(extensions);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<AAGUID> getAaguid() {
    return Optional.ofNullable(aaguid);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<SupportedCtapOptions> getOptions() {
    return Optional.ofNullable(options);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxMsgSize() {
    return Optional.ofNullable(maxMsgSize);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<CtapPinUvAuthProtocolVersion>> getPinUvAuthProtocols() {
    return Optional.ofNullable(pinUvAuthProtocols);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxCredentialCountInList() {
    return Optional.ofNullable(maxCredentialCountInList);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxCredentialIdLength() {
    return Optional.ofNullable(maxCredentialIdLength);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<AuthenticatorTransport>> getTransports() {
    return Optional.ofNullable(transports);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<List<PublicKeyCredentialParameters>> getAlgorithms() {
    return Optional.ofNullable(algorithms);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxSerializedLargeBlobArray() {
    return Optional.ofNullable(maxSerializedLargeBlobArray);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Boolean> getForcePINChange() {
    return Optional.ofNullable(forcePINChange);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMinPINLength() {
    return Optional.ofNullable(minPINLength);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getFirmwareVersion() {
    return Optional.ofNullable(firmwareVersion);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxCredBlobLength() {
    return Optional.ofNullable(maxCredBlobLength);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxRPIDsForSetMinPINLength() {
    return Optional.ofNullable(maxRPIDsForSetMinPINLength);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getPreferredPlatformUvAttempts() {
    return Optional.ofNullable(preferredPlatformUvAttempts);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<UserVerificationMethod>> getUvModality() {
    return Optional.ofNullable(uvModality);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Map<CtapCertificationId, Integer>> getCertifications() {
    return Optional.ofNullable(certifications);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getRemainingDiscoverableCredentials() {
    return Optional.ofNullable(remainingDiscoverableCredentials);
  }

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<Integer>> getVendorPrototypeConfigCommands() {
    return Optional.ofNullable(vendorPrototypeConfigCommands);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<List<String>> getAttestationFormats() {
    return Optional.ofNullable(attestationFormats);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getUvCountSinceLastPinEntry() {
    return Optional.ofNullable(uvCountSinceLastPinEntry);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<String>> getTransportsForReset() {
    return Optional.ofNullable(transportsForReset);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<String> getPinComplexityPolicyURL() {
    return Optional.ofNullable(pinComplexityPolicyURL);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Integer> getMaxPINLength() {
    return Optional.ofNullable(maxPINLength);
  }

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  public Optional<Set<Integer>> getAuthenticatorConfigCommands() {
    return Optional.ofNullable(authenticatorConfigCommands);
  }

  private static class SetFromIntJsonDeserializer
      extends JsonDeserializer<Set<UserVerificationMethod>> {
    @Override
    public Set<UserVerificationMethod> deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException {
      final int bitset = p.getNumberValue().intValue();
      return Arrays.stream(UserVerificationMethod.values())
          .filter(uvm -> (uvm.getValue() & bitset) != 0)
          .collect(Collectors.toSet());
    }
  }

  private static class IntFromSetJsonSerializer
      extends JsonSerializer<Set<UserVerificationMethod>> {
    @Override
    public void serialize(
        Set<UserVerificationMethod> value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeNumber(
          value.stream().reduce(0, (acc, next) -> acc | next.getValue(), (a, b) -> a | b));
    }
  }

  @Value
  @JsonDeserialize(using = PublicKeyCredentialParametersIgnoringUnknownValues.Deserializer.class)
  private static class PublicKeyCredentialParametersIgnoringUnknownValues {
    PublicKeyCredentialParameters value;

    private static class Deserializer
        extends JsonDeserializer<PublicKeyCredentialParametersIgnoringUnknownValues> {
      @Override
      public PublicKeyCredentialParametersIgnoringUnknownValues deserialize(
          JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        try {
          return new PublicKeyCredentialParametersIgnoringUnknownValues(
              p.readValueAs(PublicKeyCredentialParameters.class));
        } catch (IOException e) {
          return null;
        }
      }
    }
  }

  private static class ListPublicKeyCredentialParametersIgnoringUnknownValuesDeserializer
      extends JsonDeserializer<List<PublicKeyCredentialParameters>> {
    @Override
    public List<PublicKeyCredentialParameters> deserialize(
        JsonParser p, DeserializationContext ctxt) throws IOException {
      PublicKeyCredentialParametersIgnoringUnknownValues[] pkcpiuvs =
          p.readValueAs(PublicKeyCredentialParametersIgnoringUnknownValues[].class);
      return Arrays.stream(pkcpiuvs)
          .flatMap(
              pkcpiuv -> {
                if (pkcpiuv != null && pkcpiuv.value != null) {
                  return Stream.of(pkcpiuv.value);
                } else {
                  return Stream.empty();
                }
              })
          .collect(Collectors.toList());
    }
  }
}
