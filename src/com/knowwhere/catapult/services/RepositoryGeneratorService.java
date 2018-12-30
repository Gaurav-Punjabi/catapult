package com.knowwhere.catapult.services;

import com.knowwhere.catapult.constants.CodeConstants;
import com.knowwhere.catapult.models.Catapult;

import static com.knowwhere.catapult.constants.CommonConstants.NEXT_LINE;
import static com.knowwhere.catapult.constants.CommonConstants.SEMI_COLON;

public class RepositoryGeneratorService {
      private Catapult catapult;
      public RepositoryGeneratorService(final Catapult catapult) {
            this.catapult = catapult;
      }

      public String generateRepository(String modelName) {
            StringBuffer repositoryCode = new StringBuffer();

            // Adding Package
            repositoryCode.append("package ")
                       .append(this.catapult.getPackageName() + ".repositories")
                       .append(SEMI_COLON)
                       .append(NEXT_LINE);

            // Imports statements
            repositoryCode.append(CodeConstants.IMPORTS);
            repositoryCode.append("import org.springframework.data.jpa.repository.JpaRepository;").append(NEXT_LINE);
            repositoryCode.append("import ")
                       .append(this.catapult.getPackageName())
                       .append(".models.*;")
                        .append(NEXT_LINE);

            repositoryCode.append(NEXT_LINE);
            repositoryCode.append("public interface " + modelName + "Repository extends JpaRepository<" + modelName + ", Integer> { ")
                        .append(NEXT_LINE)
                        .append(NEXT_LINE)
                        .append('}');

            return repositoryCode.toString();
      }
}
