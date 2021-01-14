package io.supertokens.pluginInterface;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public interface RowMapper<P, Q> {
    P map(Q rs) throws Exception;

    default P mapOrThrow(Q rs) throws StorageQueryException {
        try {
            return map(rs);
        } catch (Exception e) {
            throw new StorageQueryException(e);
        }
    }
}