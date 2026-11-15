package com.municipalidad.tramite.validation;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public final class AdjuntoFileRules {

    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private AdjuntoFileRules() {}

    public static String validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "Seleccione un archivo para adjuntar.";
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            return "El archivo no puede superar los 10 MB.";
        }

        String originalName = originalFileName(file.getOriginalFilename());
        if (originalName.isBlank() || originalName.length() > 255) {
            return "El nombre del archivo debe tener entre 1 y 255 caracteres.";
        }

        String extension = extensionOf(originalName);
        if (!extension.equals("pdf") && !extension.equals("jpg")
                && !extension.equals("jpeg") && !extension.equals("png")) {
            return "Solo se permiten archivos PDF, JPG o PNG.";
        }

        try (InputStream inputStream = file.getInputStream()) {
            byte[] signature = inputStream.readNBytes(extension.equals("pdf") ? 8192 : 8);
            if (!hasValidSignature(extension, signature)) {
                return "El contenido del archivo no coincide con su formato.";
            }
        } catch (IOException exception) {
            return "No se pudo leer el archivo seleccionado.";
        }
        return null;
    }

    public static String originalFileName(String fileName) {
        if (fileName == null) {
            return "";
        }
        String normalized = fileName.replace('\\', '/');
        return normalized.substring(normalized.lastIndexOf('/') + 1);
    }

    private static String extensionOf(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex < 0 ? "" : fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
    }

    private static boolean hasValidSignature(String extension, byte[] signature) {
        if (extension.equals("pdf")) {
            byte[] pdfHeader = new byte[]{'%', 'P', 'D', 'F', '-'};
            for (int offset = 0; offset <= signature.length - pdfHeader.length; offset++) {
                if (startsWithAt(signature, pdfHeader, offset)) {
                    return true;
                }
            }
            return false;
        }
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            return startsWith(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF});
        }
        return startsWith(signature, new byte[]{
                (byte) 0x89, 'P', 'N', 'G', 13, 10, 26, 10
        });
    }

    private static boolean startsWith(byte[] value, byte[] prefix) {
        return startsWithAt(value, prefix, 0);
    }

    private static boolean startsWithAt(byte[] value, byte[] prefix, int offset) {
        if (value.length - offset < prefix.length) {
            return false;
        }
        for (int index = 0; index < prefix.length; index++) {
            if (value[offset + index] != prefix[index]) {
                return false;
            }
        }
        return true;
    }
}
