export class User {

    constructor(
      public process: string,
      public inputFile: string,
      public outputFile: string,
      public processing: Boolean,
      public toBeProcessed: Boolean,
      public processed: Boolean,
      public createdAt: string,
      public createdBy: string,
      public updatedAt: string,
      public processStartedAt: string,
      public processFinishedAt: string

    ) {  }
  
  }
  