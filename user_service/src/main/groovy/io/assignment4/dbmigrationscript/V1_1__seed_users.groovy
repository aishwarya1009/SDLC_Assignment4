package io.assignment4.dbmigrationscript

import io.assignment4.domain.RoleType
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.sql.PreparedStatement

class V1_1__seed_users extends BaseJavaMigration{

    @Override
    void migrate(Context context) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()
        String pswd = encoder.encode('password1')
        String newPerson = "INSERT INTO `user` (`version`,`email`,`password`, `first_name`, `last_name`, `role`) " +
                "VALUES (0, 'user2@email.com', '$pswd', 'FName', 'LName', '${RoleType.USER.getName()}') "

        try (PreparedStatement ps = context.getConnection()
                .prepareStatement(newPerson)) {
            ps.execute()
        }
    }
}
