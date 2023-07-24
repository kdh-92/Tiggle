import { ContentProps } from "../../utils/types";

export default function ContentSection ({theme}: ContentProps) {
    return(
        <div className="content-wrapper">
          <div
            style={{
              padding: 24,
              minHeight: 1000,
              background: theme,
            }}
          >
            여기 이제 내용 들어갈 것
          </div>
        </div>
    );
};