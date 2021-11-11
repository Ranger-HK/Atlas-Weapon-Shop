package view.tm;

public class ModelTM {
        private String modelId;
        private String modelName;

    public ModelTM() {
    }

    public ModelTM(String modelId, String modelName) {
        this.setModelId(modelId);
        this.setModelName(modelName);
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "ModelTM{" +
                "modelId='" + modelId + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
