const pfs = require("fs/promises");

const dotenv = require("dotenv");
const OpenAPI = require("openapi-typescript-codegen");

process.env.NODE_ENV === "production"
  ? dotenv.config({ path: ".env.production" })
  : dotenv.config({ path: ".env.development" });

const specURL = `${process.env.VITE_API_URL}v3/api-docs`;
const specPath = "src/generated/spec.json";

async function generate() {
  const response = await fetch(specURL);
  const data = await response.text();

  await pfs.writeFile(`${__dirname}/${specPath}`, data);

  await OpenAPI.generate({
    input: `./${specPath}`,
    output: "./src/generated",
    useUnionTypes: true,
    httpClient: "axios",
    request: "./src/query/openapi-request.ts",
  });
}

generate();
