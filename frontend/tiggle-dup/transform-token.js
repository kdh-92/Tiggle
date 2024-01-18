const { transformTokens } = require("token-transformer");
const fs = require("fs");

const INPUT_PATH = "./tokens.json";
const OUTPUT_PATH = "./src/styles/config/theme.ts";

const getJsonToken = async path => {
  const file = fs.readFileSync(path, {
    encoding: "utf8",
    flag: "r",
  });
  return JSON.parse(file);
};

const setsToUse = ["global"];
const excludes = [];
const transformerOptions = {
  expandTypography: true,
  expandShadow: true,
  expandComposition: true,
  expandBorder: true,
  preserveRawValue: false,
  throwErrorWhenNotResolved: true,
  resolveReferences: true,
};

const transform = async () => {
  const rawTokens = await getJsonToken(INPUT_PATH);
  const resolved = transformTokens(
    rawTokens,
    setsToUse,
    excludes,
    transformerOptions,
  );

  fs.writeFileSync(
    OUTPUT_PATH,
    `export const theme = ${JSON.stringify(resolved, null, 2)}`,
  );
};

transform();
